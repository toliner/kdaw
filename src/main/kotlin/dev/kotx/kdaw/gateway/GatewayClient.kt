@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package dev.kotx.kdaw.gateway

import dev.kotx.kdaw.*
import dev.kotx.kdaw.gateway.OpCode.*
import dev.kotx.kdaw.rest.*
import dev.kotx.kdaw.util.*
import dev.kotx.kdaw.util.JsonBuilder
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.*
import java.util.zip.*

@OptIn(InternalAPI::class)
class GatewayClient internal constructor(
    private val kdaw: KdawImpl
) {
    private val client = HttpClient {
        install(WebSockets)
    }

    private lateinit var session: DefaultClientWebSocketSession

    suspend fun connect() {
        Kdaw.LOGGER.debug("Connecting...")
        val gatewayUrl = kdaw.restClient.request(EndPoint(HttpMethod.Get, "/gateway"))?.getStringOrNull("url") ?: throw Exception("Failed to get gateway url.")

        session = client.request<HttpStatement>(gatewayUrl) {
            parameter("v", "9")
            parameter("encoding", "json")
            parameter("compress", "zlib-stream")
        }.receive()

        Kdaw.LOGGER.debug("The connection was successfully established.")

        if (sessionId == null) send(IDENTIFY) {
            "token" to kdaw.token
            "intents" to kdaw.intents
            "properties" to {
                "\$os" to "?"
                "\$browser" to "?"
                "\$device" to "?"
            }
        } else send(RESUME) {
            "session_id" to sessionId
            "token" to kdaw.token
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
                Kdaw.LOGGER.error("Received invalid frame.")
            }
        }
    }

    private suspend fun send(opCode: OpCode, data: JsonBuilder.() -> Unit) {
        val json = json {
            "op" to opCode.code
            "d" to data
        }.toString()

        session.send(json)

        Kdaw.LOGGER.debug("S: $json")
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

        Kdaw.LOGGER.debug("R: $text")

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

    private val eventHandlers = mutableListOf<GatewayHandler>()

    @OptIn(InternalSerializationApi::class)
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


                val handler = eventHandlers.find { it.type == type } ?: return
                handler.handle(kdaw, data)
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

