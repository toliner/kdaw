package dev.kotx.kdaw.entity

import kotlinx.serialization.json.*

interface Parseable {
    fun parse(): JsonElement
}