package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.*
import dev.kotx.kdaw.entity.channel.*
import dev.kotx.kdaw.entity.message.attachment.*
import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.channel.text.*
import dev.kotx.kdaw.entity.server.member.*
import dev.kotx.kdaw.entity.user.*
import dev.kotx.kdaw.util.*
import kotlinx.serialization.json.*
import java.time.*

class ServerMessageImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val content: String,
    override val embed: Embed,
    override val components: List<Component>,
    override val attachments: List<Attachment>,
    override val tts: Boolean,
    override val type: MessageType,
    override val author: User?,
    override val member: Member?,
    override val server: Server?,
    override val channel: TextChannel,
    override val serverChannel: ServerTextChannel?,
    override val privateChannel: PrivateTextChannel?,
    override val editedTimeStamp: Instant?,
    override val mentionsEveryone: Boolean,
    override val pinned: Boolean,
) : ServerMessage {
    override fun parse(): JsonElement = json { }
}