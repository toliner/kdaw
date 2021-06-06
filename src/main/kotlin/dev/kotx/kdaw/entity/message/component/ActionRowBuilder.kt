package dev.kotx.kdaw.entity.message.component

class ActionRowBuilder {
    private val buttons: MutableList<ButtonComponent> = mutableListOf()

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

    fun build(): ActionRowComponent = ActionRowComponent(buttons)
}