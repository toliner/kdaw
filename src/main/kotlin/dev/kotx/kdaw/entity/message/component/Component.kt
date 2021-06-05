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

    companion object {
        fun primary(customId: String, builder: ButtonComponentBuilder.() -> Unit) = ButtonComponentBuilder().apply {
            style(ComponentStyle.PRIMARY)
            customId(customId)
        }.apply(builder).build()

        fun secondary(customId: String, builder: ButtonComponentBuilder.() -> Unit) = ButtonComponentBuilder().apply {
            style(ComponentStyle.SECONDARY)
            customId(customId)
        }.apply(builder).build()

        fun success(customId: String, builder: ButtonComponentBuilder.() -> Unit) = ButtonComponentBuilder().apply {
            style(ComponentStyle.SUCCESS)
            customId(customId)
        }.apply(builder).build()

        fun danger(customId: String, builder: ButtonComponentBuilder.() -> Unit) = ButtonComponentBuilder().apply {
            style(ComponentStyle.DANGER)
            customId(customId)
        }.apply(builder).build()

        fun link(url: String, builder: ButtonComponentBuilder.() -> Unit) = ButtonComponentBuilder().apply {
            style(ComponentStyle.LINK)
            customId(url)
        }.apply(builder).build()
    }
}