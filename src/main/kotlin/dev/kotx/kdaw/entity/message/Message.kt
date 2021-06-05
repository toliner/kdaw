package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.channel.*
import dev.kotx.kdaw.entity.message.attachment.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.channel.text.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*
import java.time.*

interface Message: Entity {
    val type: MessageType

    val author: User?

    val member: Member?

    val server: Server?

    val channel: TextChannel

    val serverChannel: ServerTextChannel?

    val privateChannel: PrivateTextChannel?

    val content: String

    val embeds: List<Embed>

    val attachments: List<Attachment>

    val editedTimeStamp: Instant?

    val mentionsEveryone: Boolean

    val pinned: Boolean

    val tts: Boolean
}