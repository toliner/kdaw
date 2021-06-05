package dev.kotx.kdaw.entity.message

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.message.embed.*

class MessageBuilder {
    var content: String? = null
        private set

    private var tts: Boolean = false

    private val embeds: MutableList<Embed> = mutableListOf()

    fun content(content: String): MessageBuilder {
        this.content = content
        return this
    }

    fun append(content: String): MessageBuilder {
        this.content += content
        return this
    }

    fun appendln(content: String): MessageBuilder {
        this.content += "$content\n"
        return this
    }

    fun mention(vararg target: Mentionable): MessageBuilder {
        content += target.joinToString(" ") { it.mention }
        return this
    }

    fun mentionln(vararg target: Mentionable): MessageBuilder {
        content += "${target.joinToString(" ") { it.mention }}\n"
        return this
    }

    fun mentionUser(vararg target: Long): MessageBuilder {
        content += target.joinToString(" ") { "<@$it>" }
        return this
    }

    fun mentionMember(vararg target: Long): MessageBuilder {
        content += target.joinToString(" ") { "<@!$it>" }
        return this
    }

    fun mentionChannel(vararg target: Long): MessageBuilder {
        content += target.joinToString(" ") { "<#$it>" }
        return this
    }

    fun mentionRole(vararg target: Long): MessageBuilder {
        content += target.joinToString(" ") { "<@&$it>" }
        return this
    }

    fun mentionEmoji(name: String, id: Long): MessageBuilder {
        content += "<:$name:$id>"
        return this
    }

    fun mentionAnimatedEmoji(name: String, id: Long): MessageBuilder {
        content += "<a:$name:$id>"
        return this
    }

    fun mentionUserln(vararg target: Long): MessageBuilder {
        content += "${target.joinToString(" ") { "<@$it>" }}\n"
        return this
    }

    fun mentionMemberln(vararg target: Long): MessageBuilder {
        content += "${target.joinToString(" ") { "<@!$it>" }}\n"
        return this
    }

    fun mentionChannelln(vararg target: Long): MessageBuilder {
        content += "${target.joinToString(" ") { "<#$it>" }}\n"
        return this
    }

    fun mentionRoleln(vararg target: Long): MessageBuilder {
        content += "${target.joinToString(" ") { "<@&$it>" }}\n"
        return this
    }

    fun mentionEmojiln(name: String, id: Long): MessageBuilder {
        content += "<:$name:$id>\n"
        return this
    }

    fun mentionAnimatedEmojiln(name: String, id: Long): MessageBuilder {
        content += "<a:$name:$id>\n"
        return this
    }

    operator fun String.unaryPlus(): MessageBuilder {
        this@MessageBuilder.content += "$this\n"
        return this@MessageBuilder
    }

    fun enableTTS(): MessageBuilder {
        tts = true
        return this
    }

    fun embed(embed: Embed): MessageBuilder {
        embeds.add(embed)
        return this
    }

    fun embed(builder: EmbedBuilder.() -> Unit): MessageBuilder {
        embeds.add(EmbedBuilder().apply(builder).build())
        return this
    }

    operator fun (EmbedBuilder.() -> Unit).unaryPlus(): MessageBuilder {
        embeds.add(EmbedBuilder().apply(this).build())
        return this@MessageBuilder
    }
}