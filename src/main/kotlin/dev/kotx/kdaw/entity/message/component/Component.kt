package dev.kotx.kdaw.entity.message.component

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.emoji.*

interface Component: Parseable {
    val type: ComponentType
    val style: ComponentStyle?
    val label: String?
    val emoji: PartialEmoji?
    val customId: String?
    val url: String?
    val disabled: Boolean?
    val components: List<Component>?
}