package dev.kotx.kdaw.entity.server.emoji

import dev.kotx.kdaw.entity.*

interface PartialEmoji: Parseable {
    val name: String
    val id: Long?
    val animated: Boolean?
}