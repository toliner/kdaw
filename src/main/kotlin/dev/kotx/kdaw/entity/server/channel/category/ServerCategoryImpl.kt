package dev.kotx.kdaw.entity.server.channel.category

import dev.kotx.kdaw.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.permission.*

class ServerCategoryImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val name: String,
    override val server: Server,
    override val position: Int,
    override val memberPermissionOverwrites: List<MemberPermissionOverwrite>,
    override val rolePermissionOverwrites: List<RolePermissionOverwrite>
) : ServerCategory {

}