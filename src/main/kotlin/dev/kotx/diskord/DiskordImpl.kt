package dev.kotx.diskord

import dev.kotx.diskord.gateway.*
import dev.kotx.diskord.rest.*

class DiskordImpl(token: String) : Diskord(token) {
    val restClient = RestClient(this)
    val gatewayClient = GatewayClient(this)

    override suspend fun test() {
        gatewayClient.connect()
    }
}