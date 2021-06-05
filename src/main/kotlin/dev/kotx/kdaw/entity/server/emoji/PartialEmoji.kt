package dev.kotx.kdaw.entity.server.emoji

interface PartialEmoji {
    val name: String
    val id: Long?
    val animated: Boolean?
}