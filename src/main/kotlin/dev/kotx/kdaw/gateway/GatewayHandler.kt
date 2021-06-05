package dev.kotx.kdaw.gateway

import dev.kotx.kdaw.*
import kotlinx.serialization.json.*

abstract class GatewayHandler(val type: String) {
    abstract fun handle(kdaw: KdawImpl, data: JsonObject?)
}