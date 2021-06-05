package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*

interface Message: Entity {
    val type: MessageType

    val author: User?

    val member: Member?
}