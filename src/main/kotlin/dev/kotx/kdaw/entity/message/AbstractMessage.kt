package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*

interface AbstractMessage: Parseable {
    val content: String?
    val tts: Boolean?
    val embed: Embed?
    val components: List<Component>?
}