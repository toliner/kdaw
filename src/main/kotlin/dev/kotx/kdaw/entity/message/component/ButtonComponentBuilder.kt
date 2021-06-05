package dev.kotx.kdaw.entity.message.component

import dev.kotx.kdaw.entity.server.emoji.*

class ButtonComponentBuilder {
    private var label: String? = null
    private var style: ComponentStyle? = ComponentStyle.PRIMARY
    private var emoji: PartialEmoji? = null
    private var customId: String? = null
    private var url: String? = null
    private var disabled: Boolean = false

    fun label(label: String): ButtonComponentBuilder {
        this.label = label
        return this
    }

    fun style(style: ComponentStyle): ButtonComponentBuilder {
        this.style = style
        return this
    }

    fun emoji(emoji: PartialEmoji): ButtonComponentBuilder {
        this.emoji = emoji
        return this
    }

    fun customId(customId: String): ButtonComponentBuilder {
        this.customId = customId
        return this
    }

    fun url(url: String): ButtonComponentBuilder {
        this.url = url
        return this
    }

    fun disable(): ButtonComponentBuilder {
        this.disabled = true
        return this
    }

    fun build(): ButtonComponent = ButtonComponent(
        style, label, emoji, customId, url, disabled
    )
}