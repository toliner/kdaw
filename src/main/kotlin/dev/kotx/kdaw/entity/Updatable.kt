package dev.kotx.kdaw.entity

import kotlinx.serialization.json.*

interface Updatable : Entity {
    fun update(data: JsonObject)
}