package dev.kotx.kdaw.entity.server.permission

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.role.*
import dev.kotx.kdaw.util.*

abstract class PermissionOverwrite(
    val entity: Permissionable,
    val allow: List<Permission>,
    val deny: List<Permission>,
) {

    fun parse() = json {
        "id" to if (entity is Role) 0 else 1
        "type" to 0
        "allow" to Permission.calculate(allow)
        "deny" to Permission.calculate(deny)
    }

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