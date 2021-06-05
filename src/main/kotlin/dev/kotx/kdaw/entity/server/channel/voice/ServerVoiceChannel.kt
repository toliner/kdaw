package dev.kotx.kdaw.entity.server.channel.voice

import dev.kotx.kdaw.entity.server.channel.*
import dev.kotx.kdaw.entity.server.channel.category.*

interface ServerVoiceChannel : ServerChannel {
    val category: ServerCategory?

    val bitrate: Int

    val userLimit: Int
}