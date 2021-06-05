package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.message.component.*
import dev.kotx.kdaw.entity.message.embed.*

class MessageBuilder {
    var content: String? = null
        private set

    private var tts: Boolean = false

    private var embed: Embed? = null
    private val components: MutableList<Component> = mutableListOf()

    fun content(content: String): MessageBuilder {
        this.content = content
        return this
    }

    fun append(content: String): MessageBuilder {
        if (this.content == null) this.content = ""
        this.content += content
        return this
    }

    fun appendln(content: String): MessageBuilder {
        append("$content\n")
        return this
    }

    fun mention(vararg target: Mentionable): MessageBuilder {
        append(target.joinToString(" ") { it.mention })
        return this
    }

    fun mentionln(vararg target: Mentionable): MessageBuilder {
        appendln(target.joinToString(" ") { it.mention })
        return this
    }

    fun mentionUser(vararg target: Long): MessageBuilder {
        append(target.joinToString(" ") { "<@$it>" })
        return this
    }

    fun mentionMember(vararg target: Long): MessageBuilder {
        append(target.joinToString(" ") { "<@$it>" })
        return this
    }

    fun mentionChannel(vararg target: Long): MessageBuilder {
        append(target.joinToString(" ") { "<#$it>" })
        return this
    }

    fun mentionRole(vararg target: Long): MessageBuilder {
        append(target.joinToString(" ") { "<@&$it>" })
        return this
    }

    fun mentionEmoji(name: String, id: Long): MessageBuilder {
        append("<:$name:$id>")
        return this
    }

    fun mentionAnimatedEmoji(name: String, id: Long): MessageBuilder {
        append("<a:$name:$id>")
        return this
    }

    fun mentionUserln(vararg target: Long): MessageBuilder {
        appendln(target.joinToString(" ") { "<@$it>" })
        return this
    }

    fun mentionMemberln(vararg target: Long): MessageBuilder {
        appendln(target.joinToString(" ") { "<@!$it>" })
        return this
    }

    fun mentionChannelln(vararg target: Long): MessageBuilder {
        appendln(target.joinToString(" ") { "<#$it>" })
        return this
    }

    fun mentionRoleln(vararg target: Long): MessageBuilder {
        appendln(target.joinToString(" ") { "<@&$it>" })
        return this
    }

    fun mentionEmojiln(name: String, id: Long): MessageBuilder {
        appendln("<:$name:$id>")
        return this
    }

    fun mentionAnimatedEmojiln(name: String, id: Long): MessageBuilder {
        appendln("<a:$name:$id>")
        return this
    }

    operator fun String.unaryPlus(): MessageBuilder {
        appendln(this)
        return this@MessageBuilder
    }

    fun enableTTS(): MessageBuilder {
        tts = true
        return this
    }

    fun embed(embed: Embed): MessageBuilder {
        this.embed = embed
        return this
    }

    fun embed(builder: EmbedBuilder.() -> Unit): MessageBuilder {
        this.embed = EmbedBuilder().apply(builder).build()
        return this
    }

    operator fun (EmbedBuilder.() -> Unit).unaryPlus(): MessageBuilder {
        this@MessageBuilder.embed = EmbedBuilder().apply(this).build()
        return this@MessageBuilder
    }

    fun component(builder: ActionRowBuilder.() -> Unit): MessageBuilder {
        components.add(ActionRowBuilder().apply(builder).build())
        return this
    }

    fun build(): AbstractMessage = AbstractMessageImpl(
        content, tts, embed, components
    )
}