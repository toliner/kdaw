package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.message.attachment.*
import dev.kotx.kdaw.entity.message.embed.*

interface AbstractMessage {
    val content: String
    val embeds: List<Embed>
    val attachments: List<Attachment>
    val tts: Boolean
}