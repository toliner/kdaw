package dev.kotx.kdaw.entity.server.permission

import dev.kotx.kdaw.entity.server.member.*

class MemberPermissionOverwrite(member: Member, allow: List<Permission> = emptyList(), deny: List<Permission> = emptyList()) : PermissionOverwrite(member, allow, deny)