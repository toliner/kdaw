package dev.kotx.kdaw.entity.message.component

import dev.kotx.kdaw.entity.server.emoji.*
import dev.kotx.kdaw.util.*
import kotlinx.serialization.json.*

class ButtonComponent(
    override val style: ComponentStyle?,
    override val label: String?,
    override val emoji: PartialEmoji?,
    override val customId: String?,
    override val url: String?,
    override val disabled: Boolean?,
) : Component {
    override val type: ComponentType = ComponentType.BUTTON
    override val components: List<Component>? = null

    override fun parse(): JsonElement = json {
        "type" to type.value
        style?.also { "style" to it.value }
        label?.also { "label" to it }
        emoji?.also { "emoji" to it.parse() }
        customId?.also { "custom_id" to it }
        url?.also { "url" to it }
        disabled?.also { "disabled" to it }
    }
}