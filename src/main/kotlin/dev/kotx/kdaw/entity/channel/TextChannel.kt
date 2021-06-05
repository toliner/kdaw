package dev.kotx.kdaw.entity.channel

import dev.kotx.kdaw.entity.*

interface TextChannel: Entity {
    suspend fun send(text: String)
}