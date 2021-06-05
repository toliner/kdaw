package dev.kotx.kdaw.entity.message.embed

import java.awt.*
import java.time.*

interface Embed {
    val title: String?
    val description: String?

    val url: String?
    val color: Color?
    val timestamp: Instant?
    val imageUrl: String?
    val thumbnailUrl: String?

    val fields: List<Field>
    val footer: Footer?
    val author: Author?
}