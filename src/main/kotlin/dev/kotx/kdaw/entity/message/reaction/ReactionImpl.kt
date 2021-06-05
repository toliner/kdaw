package dev.kotx.kdaw.entity.message.reaction

import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.emoji.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*

class ReactionImpl(
    override val server: Server?,
    override val userId: Long,
    override val channelId: Long,
    override val messageId: Long,
    override val author: User?,
    override val member: Member?,
    override val emoji: PartialEmoji
) : Reaction