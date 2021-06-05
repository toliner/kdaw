package dev.kotx.kdaw.entity.server.member

import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.permission.*
import dev.kotx.kdaw.entity.server.role.*
import dev.kotx.kdaw.entity.user.*
import java.time.*

interface Member: User {
    val server: Server

    val nickname: String?

    val joinedAt: Instant

    val status: UserStatus

    val roles: List<Role>

    val isOwner: Boolean
        get() = id == server.ownerId

    val isAdmin: Boolean
        get() = isOwner || Permission.ADMINISTRATOR in permissions

    val permissions: List<Permission>
        get() = roles.flatMap { it.permissions }
}