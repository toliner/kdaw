package dev.kotx.kdaw.entity.image

interface Image {
    val url: String

    suspend fun bytes(): ByteArray
}