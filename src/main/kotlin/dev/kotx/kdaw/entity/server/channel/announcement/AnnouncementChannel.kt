package dev.kotx.kdaw.entity.server.channel.announcement

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.channel.*
import dev.kotx.kdaw.entity.server.channel.*
import dev.kotx.kdaw.entity.server.channel.category.*

interface AnnouncementChannel : ServerChannel, Mentionable, TextChannel {
    val topic: String?

    val nsfw: Boolean

    val category: ServerCategory?

    override val mention: String
        get() = "<#$id>"

    suspend fun crosspostMessage(messageId: Long)
}