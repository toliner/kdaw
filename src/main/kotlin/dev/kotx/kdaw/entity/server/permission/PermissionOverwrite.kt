package dev.kotx.kdaw.entity.server.permission

import dev.kotx.kdaw.entity.*

abstract class PermissionOverwrite(
    val entity: Permissionable,
    val allow: List<Permission>,
    val deny: List<Permission>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PermissionOverwrite) return false

        if (entity != other.entity) return false
        if (allow != other.allow) return false
        if (deny != other.deny) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entity.hashCode()
        result = 31 * result + allow.hashCode()
        result = 31 * result + deny.hashCode()
        return result
    }
}