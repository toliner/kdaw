package dev.kotx.kdaw.entity.message.attachment

import dev.kotx.kdaw.*

class AttachmentImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val fileName: String,
    override val size: Int,
    override val url: String,
    override val proxyUrl: String,
    override val height: Int?,
    override val width: Int?
) : Attachment