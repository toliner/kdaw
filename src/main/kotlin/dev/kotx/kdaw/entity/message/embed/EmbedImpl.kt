package dev.kotx.kdaw.entity.message.embed

import java.awt.*
import java.time.*

class EmbedImpl(
    override val title: String?,
    override val url: String?,
    override val author: Author?,
    override val description: String?,
    override val fields: List<Field>,
    override val color: Color?,
    override val image: String?,
    override val thumbnail: String?,
    override val timestamp: Instant?,
    override val footer: Footer?
) : Embed