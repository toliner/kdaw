package dev.kotx.kdaw.entity.message.component

class ActionRowBuilder {
    private val buttons: MutableList<ButtonComponent> = mutableListOf()

    operator fun ButtonComponent.unaryPlus(): ActionRowBuilder {
        this@ActionRowBuilder.buttons.add(this)
        return this@ActionRowBuilder
    }

    fun build(): ActionRowComponent = ActionRowComponent(buttons)
}