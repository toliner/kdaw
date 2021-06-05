package dev.kotx.kdaw.entity.message.reaction

import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.emoji.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*

interface Reaction {
    val server: Server?
    val userId: Long
    val channelId: Long
    val messageId: Long
    val author: User?
    val member: Member?
    val emoji: PartialEmoji
}