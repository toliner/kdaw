package dev.kotx.kdaw.entity.message.embed

import dev.kotx.kdaw.entity.*
import java.awt.*
import java.time.*

interface Embed: Parseable {
    val title: String?
    val url: String?
    val author: Author?
    val description: String?

    val fields: List<Field>?

    val color: Color?
    val image: String?
    val thumbnail: String?
    val timestamp: Instant?

    val footer: Footer?
}