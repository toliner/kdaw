package dev.kotx.kdaw.entity.server.channel.text

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.channel.*
import dev.kotx.kdaw.entity.server.channel.*
import dev.kotx.kdaw.entity.server.channel.category.*

interface ServerTextChannel : ServerChannel, Mentionable, TextChannel{
    val topic: String?

    val nsfw: Boolean

    val rateLimit: Int

    val category: ServerCategory?

    override val mention: String
        get() = "<#$id>"
}