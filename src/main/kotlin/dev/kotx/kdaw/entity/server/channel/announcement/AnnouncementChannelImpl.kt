package dev.kotx.kdaw.entity.server.channel.announcement

import dev.kotx.kdaw.*
import dev.kotx.kdaw.entity.message.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.channel.category.*
import dev.kotx.kdaw.entity.server.permission.*
import dev.kotx.kdaw.rest.*

class AnnouncementChannelImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val name: String,
    override val server: Server,
    override val position: Int,
    override val memberPermissionOverwrites: List<MemberPermissionOverwrite>,
    override val rolePermissionOverwrites: List<RolePermissionOverwrite>,
    override val topic: String?,
    override val nsfw: Boolean,
    override val category: ServerCategory?
) : AnnouncementChannel {
    override suspend fun send(message: AbstractMessage) {
        val data = message.parse()
        kdaw.restClient.request(EndPoint.Channel.Message.Create(id), data)
    }

    override suspend fun send(builder: MessageBuilder.() -> Unit) {
        send(MessageBuilder().apply(builder).build())
    }

    override suspend fun send(text: String) {
        send {
            +text
        }
    }

    override suspend fun embed(builder: EmbedBuilder.() -> Unit) {
        send {
            embed(builder)
        }
    }

    override suspend fun crosspostMessage(messageId: Long) {
        kdaw.restClient.request(EndPoint.Channel.Message.Crosspost(id, messageId))
    }
}