package dev.kotx.kdaw.entity.message.component

class ActionRowBuilder {
    private val buttons: MutableList<ButtonComponent> = mutableListOf()

    fun add(component: ButtonComponent): ActionRowBuilder {
        buttons.add(component)
        return this
    }

    operator fun ButtonComponent.unaryPlus(): ActionRowBuilder {
        this@ActionRowBuilder.buttons.add(this)
        return this@ActionRowBuilder
    }

    fun build(): ActionRowComponent = ActionRowComponent(buttons)
}