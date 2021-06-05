package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.channel.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.channel.text.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*
import java.time.*

interface ServerMessage: Entity, AbstractMessage {
    val type: MessageType

    val author: User?

    val member: Member?

    val server: Server?

    val channel: TextChannel

    val serverChannel: ServerTextChannel?

    val privateChannel: PrivateTextChannel?

    val editedTimeStamp: Instant?

    val mentionsEveryone: Boolean

    val pinned: Boolean
}