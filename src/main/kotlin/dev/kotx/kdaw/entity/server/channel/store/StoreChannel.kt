package dev.kotx.kdaw.entity.server.channel.store

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.channel.*
import dev.kotx.kdaw.entity.server.channel.category.*

interface StoreChannel: ServerChannel, Mentionable {
    val nsfw: Boolean

    val category: ServerCategory?

    override val mention: String
        get() = "<#$id>"
}