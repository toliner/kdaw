package dev.kotx.kdaw.entity.server.channel

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.permission.*

interface ServerChannel : Nameable, Entity {
    val server: Server

    val position: Int

    val memberPermissionOverwrites: List<MemberPermissionOverwrite>
    val rolePermissionOverwrites: List<RolePermissionOverwrite>
}