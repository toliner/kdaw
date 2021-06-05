package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.util.*
import kotlinx.serialization.json.*

class AbstractMessageImpl(
    override val content: String?,
    override val tts: Boolean?,
    override val embed: Embed?,
    override val components: List<Component>?,
) : AbstractMessage {
    override fun parse(): JsonElement = json {
        content?.also { "content" to it }
        tts?.also { "tts" to it }
        embed?.also { "embed" to it.parse() }
        components?.also { "components" to jsonArray {
            it.forEach {
                +it.parse()
            }
        } }
    }
}