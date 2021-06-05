package dev.kotx.kdaw.entity.user

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.image.*

interface User: Entity, Mentionable, Nameable, Permissionable {
    val bot: Boolean
    val avatar: Image
    val discriminator: String

    override val mention: String
        get() = "<@$id>"

    val tag: String
        get() = "$name$discriminator"
}