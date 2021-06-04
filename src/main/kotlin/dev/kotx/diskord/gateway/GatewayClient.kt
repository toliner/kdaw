package dev.kotx.diskord.gateway

import dev.kotx.diskord.*
import dev.kotx.diskord.gateway.OpCode.*
import dev.kotx.diskord.rest.*
import dev.kotx.diskord.util.*
import dev.kotx.diskord.util.JsonBuilder
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.serialization.json.*
import java.io.*
import java.util.zip.*

class GatewayClient(
    val diskord: DiskordImpl
) {
    private val client = HttpClient {
        install(WebSockets)
    }

    private lateinit var session: DefaultClientWebSocketSession

    suspend fun connect() {
        Diskord.LOGGER.debug("Connecting...")
        val gatewayUrl = diskord.restClient.request(EndPoint(HttpMethod.Get, "/gateway"))?.getStringOrNull("url") ?: throw Exception("Failed to get gateway url.")

        session = client.request<HttpStatement>(gatewayUrl) {
            parameter("v", "9")
            parameter("encoding", "json")
            parameter("compress", "zlib-stream")
        }.receive()

        Diskord.LOGGER.debug("The connection was successfully established.")

        if (sessionId == null) send(IDENTIFY) {
            "token" to diskord.token
            "intents" to 513
            "properties" to {
                "\$os" to "?"
                "\$browser" to "?"
                "\$device" to "?"
            }
        } else send(RESUME) {
            "session_id" to sessionId
            "token" to diskord.token
            "seq" to lastSequence
        }

        session.incoming.consumeEach {
            scope.launch {
                onFrame(it)
            }
        }
    }

    private suspend fun onFrame(frame: Frame) {
        when (frame) {
            is Frame.Binary -> onBinary(frame.data)
            is Frame.Close -> onClose()
            else -> {
                Diskord.LOGGER.error("Received invalid frame.")
            }
        }
    }

    private suspend fun send(opCode: OpCode, data: JsonBuilder.() -> Unit) {
        val json = json {
            "op" to opCode.code
            "d" to data
        }.toString()

        session.send(json)

        Diskord.LOGGER.debug("S: $json")
    }

    private val buffer = mutableListOf<ByteArray>()
    private val suffix = hex("0000ffff")
    private val inflater = Inflater()
    private suspend fun onBinary(data: ByteArray) {
        buffer.add(data)

        if (data.size < 4 || !data.takeLast(4).toByteArray().contentEquals(suffix)) return

        val text = ByteArrayOutputStream().use {
            fun Collection<ByteArray>.concat(): ByteArray {
                val length = sumOf { it.size }
                val output = ByteArray(length)

                var pos = 0
                forEach {
                    System.arraycopy(it, 0, output, pos, it.size)
                    pos += it.size
                }

                return output
            }

            InflaterOutputStream(it, inflater).use { it.write(buffer.concat()) }
            it.toString(Charsets.UTF_8.toString())
        }

        buffer.clear()

        Diskord.LOGGER.debug("R: $text")

        val json = try {
            text.asJsonObject()
        } catch (e: Exception) {
            return
        }

        onJson(json)
    }

    private val scope = CoroutineScope(Dispatchers.Default + CoroutineName("Gateway Client"))
    private var heartbeatTask: Job? = null
    private var heartbeatAckReceived = false
    private var lastSequence: Int? = null
    private var ready = false
    private var sessionId: String? = null

    private suspend fun onJson(payload: JsonObject) {
        val opCode = values().find { it.code == payload.getInt("op") }
        val data = payload.getObjectOrNull("d")

        when (opCode) {
            DISPATCH -> {
                val type = payload.getString("t")
                lastSequence = payload.getInt("s")

                if (type == "READY" || type == "RESUMED") {
                    ready = true

                    data?.getStringOrNull("session_id")?.also { sessionId = it }
                }
            }

            HEARTBEAT -> {

            }

            RECONNECT -> {
                session.close(CloseReason(4001, "Received reconnect request."))
            }

            INVALID_SESSION -> {
                session.close(CloseReason(4001, "Invalid session."))
            }

            HELLO -> {
                val period = data!!.getLong("heartbeat_interval")

                heartbeatAckReceived = true
                heartbeatTask?.cancel()

                heartbeatTask = scope.timer(period) {
                    if (heartbeatAckReceived) {
                        heartbeatAckReceived = false
                        send(HEARTBEAT) {
                            lastSequence?.also { "d" to it }
                        }
                    } else {
                        session.close(CloseReason(4000, "Heartbeat ACK wasn't received."))
                    }
                }
            }

            HEARTBEAT_ACK -> {
                heartbeatAckReceived = true
            }
        }
    }

    private suspend fun onClose() {
        ready = false
        heartbeatTask?.cancel()
        buffer.clear()

        inflater.reset()

        connect()
    }
}

