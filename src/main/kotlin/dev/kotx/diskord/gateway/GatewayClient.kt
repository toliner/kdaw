package dev.kotx.diskord.gateway

import dev.kotx.diskord.*
import dev.kotx.diskord.util.*
import dev.kotx.diskord.util.JsonBuilder
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.*
import kotlinx.serialization.json.*
import java.io.*
import java.util.*
import java.util.concurrent.atomic.*
import java.util.zip.*
import kotlin.collections.ArrayDeque
import kotlin.coroutines.*
import kotlin.system.*

class GatewayClient(
    diskord: DiskordImpl
) {
    private val client = HttpClient {
        expectSuccess = false

        install(WebSockets)
    }

    @Volatile
    private var ready: Boolean = false

    @Volatile
    private var sessionId: String? = null

    @Volatile
    private var lastSequence: Int? = null

    @Volatile
    private var heartbeatAckReceived: Boolean = false

    @Volatile
    private var heartbeatTask: Job? = null

    private val scope = CoroutineScope(Dispatchers.Default + CoroutineName("Gateway Client"))
    private val buffer = mutableListOf<ByteArray>()
    private val inflater = Inflater()
    private val mutex = Mutex()
    private val sendChannel = Channel<String>(Channel.UNLIMITED)
    private val nonceSeed = AtomicLong()

    data class MemberChunkRequest(val nonce: String, val handler: Continuation<List<JsonObject>>)
    data class PostponedServerEvent(val eventType: String, val data: JsonObject)

    private val memberChunkRequests = LinkedList<MemberChunkRequest>()
    private val postponedServerEvents = HashMap<Long, ArrayDeque<PostponedServerEvent>>()

    private lateinit var session: DefaultClientWebSocketSession

    suspend fun connect() {
        session = client.webSocketSession {
            parameter("v", "9")
            parameter("encoding", "json")
            parameter("compress", "zlib-stream")

            url("wss", "gateway.discord.gg")
        }

        session.incoming.consumeEach {
            when (it) {
                is Frame.Binary -> onBinary(it.data)
            }
        }

        scope.launch(newSingleThreadContext("Gateway Packet Dispatcher")) {
            sendChannel.consumeEach {
                while (!ready) {
                    delay(10)
                }

                session.send(it)
            }
        }
    }

    private suspend fun onBinary(binary: ByteArray) {
        buffer.add(binary)

        if (binary.size < 4 || !binary.takeLastAsByteArray(4).contentEquals(hex("0000ffff")))
            return

        val text = ByteArrayOutputStream().use { out ->
            try {
                InflaterOutputStream(out, inflater).use { it.write(buffer.concat()) }
                out.toString(Charsets.UTF_8)
            } catch (e: Exception) {
                Diskord.LOGGER.error("Decompression payload failed.", e)
                return
            }
            buffer.clear()
        }

        val payload = text.asJsonObject()
        val opCode = OpCode.values().find { it.code == payload.getInt("op") }
        val data = payload.getObjectOrNull("d")
        Diskord.LOGGER.debug("Received payload: $payload")

        when (opCode) {
            OpCode.HELLO -> {
                Diskord.LOGGER.debug("Starting heartbeat")

                val period = data!!.getLong("heartbeat_interval")
                heartbeatAckReceived = true

                heartbeatTask?.cancel()
                heartbeatTask = scope.timer(period) {
                    if (heartbeatAckReceived) {
                        heartbeatAckReceived = false

                    }
                }
            }

            OpCode.RECONNECT -> session.close(CloseReason(4000, "Received Reconnect Request"))
            OpCode.INVALID_SESSION -> session.close(CloseReason(4990, "Invalid Session"))
            OpCode.HEARTBEAT_ACK -> heartbeatAckReceived = true
            OpCode.DISPATCH -> {
                val eventType = payload.getString("t")
                lastSequence = payload.getInt("s")
                if (eventType == "READY" || eventType == "RESUMED") {
                    ready = true

                    data?.getStringOrNull("session_id")?.let { sessionId = it }
                }

                //handleEvent
            }

            else -> {

            }
        }
    }

    private fun queue(opCode: OpCode, data: JsonObject = JsonObject(emptyMap())) {
        sendChannel.trySend(json {
            "op" to opCode.code
            "d" to data
        }.toString())
    }

    private fun queue(opCode: OpCode, block: JsonBuilder.() -> Unit) {
        queue(opCode, json(block))
    }

    private suspend fun send(opCode: OpCode, data: JsonObject = JsonObject(emptyMap())) {
        session.send(json {
            "op" to opCode.code
            "d" to data
        }.toString())
    }

    private suspend fun send(opCode: OpCode, block: JsonBuilder.() -> Unit) {
        send(opCode, json(block))
    }

    private fun Collection<ByteArray>.concat(): ByteArray {
        val length = sumOf { it.size }
        val output = ByteArray(length)

        var pos = 0
        forEach {
            System.arraycopy(it, 0, output, pos, it.size)
            pos += it.size
        }

        return output
    }


    private fun ByteArray.takeLastAsByteArray(n: Int): ByteArray {
        val result = ByteArray(n)
        for (i in 0 until n) {
            result[i] = this[size - n + i]
        }
        return result
    }

    private fun CoroutineScope.timer(
        interval: Long,
        fixedRate: Boolean = true,
        context: CoroutineContext = EmptyCoroutineContext,
        action: suspend TimerScope.() -> Unit
    ) = launch(context) {
        val scope = TimerScope()

        while (isActive) {
            val time = measureTimeMillis {
                try {
                    action(scope)
                } catch (ex: Exception) {
                    Diskord.LOGGER.error("Coroutine Timer", ex)
                }
            }

            if (scope.isCanceled) {
                break
            }

            if (fixedRate) {
                delay(maxOf(0, interval - time))
            } else {
                delay(interval)
            }
        }
    }


    class TimerScope {
        var isCanceled: Boolean = false
            private set

        fun cancel() {
            isCanceled = true
        }
    }
}