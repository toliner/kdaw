package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*

interface AbstractMessage {
    val content: String?
    val embeds: List<Embed>?
    val components: List<Component>?
    val tts: Boolean?
}