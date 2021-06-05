package dev.kotx.kdaw

import dev.kotx.kdaw.event.*
import dev.kotx.kdaw.gateway.*
import dev.kotx.kdaw.rest.*
import kotlin.reflect.*

class KdawImpl(token: String, val listeners: MutableMap<KClass<out Event>, KFunction<Unit>>, val intents: Int) : Kdaw(token) {
    val restClient = RestClient(this)
    val gatewayClient = GatewayClient(this)

    override suspend fun test() {

    }
}