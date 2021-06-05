package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*

class AbstractMessageImpl(
    override val content: String?,
    override val embeds: List<Embed>?,
    override val components: List<Component>?,
    override val tts: Boolean?,
) : AbstractMessage