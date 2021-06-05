package dev.kotx.kdaw.entity.channel

import dev.kotx.kdaw.*
import dev.kotx.kdaw.entity.message.*
import dev.kotx.kdaw.entity.message.embed.*
import dev.kotx.kdaw.entity.user.*
import dev.kotx.kdaw.rest.*

class PrivateTextChannelImpl(
    override val kdaw: KdawImpl,
    override val id: Long,
    override val recipients: List<User>,
    override val owner: User?
) : PrivateTextChannel {
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
}