package dev.kotx.kdaw.entity.message.attachment

import dev.kotx.kdaw.entity.*

interface Attachment: Entity {
    val fileName: String
    val size: Int
    val url: String
    val proxyUrl: String
    val height: Int?
    val width: Int?

    val isImage: Boolean
        get() = height != null || width != null
}