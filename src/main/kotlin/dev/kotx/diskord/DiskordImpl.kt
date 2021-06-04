package dev.kotx.diskord

import dev.kotx.diskord.event.*
import dev.kotx.diskord.gateway.*
import dev.kotx.diskord.rest.*
import kotlin.reflect.*

class DiskordImpl(token: String, val listeners: MutableMap<KClass<out Event>, KFunction<Unit>>, val intents: Int) : Diskord(token) {
    val restClient = RestClient(this)
    val gatewayClient = GatewayClient(this)

    override suspend fun test() {
        gatewayClient.connect()
    }
}