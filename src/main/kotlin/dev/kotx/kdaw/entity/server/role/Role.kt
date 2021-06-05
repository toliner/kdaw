package dev.kotx.kdaw.entity.server.role

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.permission.*
import java.awt.*

interface Role: Mentionable, Nameable, Permissionable, Entity {
    val server: Server

    val permissions: List<Permission>

    val color: Color

    val position: Int

    val hoists: Boolean

    val managed: Boolean

    val mentionable: Boolean

    val isEveryone: Boolean
        get() = position == 0 && name == "@everyone"

    override val mention: String
        get() = if (isEveryone) "@everyone" else "<@&$id>"
}