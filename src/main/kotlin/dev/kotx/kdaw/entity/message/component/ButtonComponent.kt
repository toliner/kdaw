package dev.kotx.kdaw.entity.message.component

import dev.kotx.kdaw.entity.server.emoji.*

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
}