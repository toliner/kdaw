package dev.kotx.kdaw.entity.server.permission

import dev.kotx.kdaw.entity.server.role.*

class RolePermissionOverwrite(role: Role, allow: List<Permission> = emptyList(), deny: List<Permission> = emptyList()) : PermissionOverwrite(role, allow, deny)