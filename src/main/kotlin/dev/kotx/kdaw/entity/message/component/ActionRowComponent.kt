package dev.kotx.kdaw.entity.message.component

import dev.kotx.kdaw.entity.server.emoji.*
import dev.kotx.kdaw.util.*
import kotlinx.serialization.json.*

class ActionRowComponent(
    override val components: List<Component>?
) : Component {
    override val type: ComponentType = ComponentType.ACTION_ROW
    override val style: ComponentStyle? = null
    override val label: String? = null
    override val emoji: PartialEmoji? = null
    override val customId: String? = null
    override val url: String? = null
    override val disabled: Boolean? = null

    override fun parse(): JsonElement = json {
        "type" to type.value
        components?.also { "components" to jsonArray {
            it.forEach {
                +it.parse()
            }
        } }
    }
}