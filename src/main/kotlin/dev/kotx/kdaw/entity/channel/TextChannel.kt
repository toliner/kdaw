package dev.kotx.kdaw.entity.channel

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.message.*

interface TextChannel: Entity {
    suspend fun send(text: String)
    suspend fun send(message: ServerMessage)
}