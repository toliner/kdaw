package dev.kotx.kdaw.entity.server.channel.announcement

import dev.kotx.kdaw.*
import dev.kotx.kdaw.entity.message.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.entity.server.*
import dev.kotx.kdaw.entity.server.channel.category.*
import dev.kotx.kdaw.entity.server.permission.*
import dev.kotx.kdaw.rest.*
import kotlinx.serialization.json.*

class NewsChannelImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val name: String,
    override val server: Server,
    override val position: Int,
    override val memberPermissionOverwrites: List<MemberPermissionOverwrite>,
    override val rolePermissionOverwrites: List<RolePermissionOverwrite>,
    override val topic: String?,
    override val isNsfw: Boolean,
    override val category: ServerCategory?
) : NewsChannel {
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

    override suspend fun edit(editor: NewsChannelEditor.() -> Unit) {
        val result = NewsChannelEditor(this).apply(editor)
        kdaw.restClient.request(EndPoint.Channel.Modify(id)) {
            "name" to result.name
            "type" to if (result.disableNewsChannel) 0 else 5
            "position" to result.position
            "topic" to result.topic
            "nsfw" to result.isNsfw
            "permission_overwrites" to (result.memberPermissionOverwrites + result.rolePermissionOverwrites).map { it.parse() }
            "parent_id" to result.category?.id
        }
    }

    override fun update(data: JsonObject) {

    }
}