package dev.kotx.kdaw.entity.channel

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.message.*
import dev.kotx.kdaw.entity.message.embed.*

interface TextChannel : Entity {
    suspend fun send(text: String)
    suspend fun send(message: AbstractMessage)
    suspend fun send(builder: MessageBuilder.() -> Unit)
    suspend fun embed(builder: EmbedBuilder.() -> Unit)
}