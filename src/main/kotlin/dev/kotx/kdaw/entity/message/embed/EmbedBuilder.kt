package dev.kotx.kdaw.entity.message.embed

import dev.kotx.kdaw.entity.*
import java.awt.*
import java.time.*

class EmbedBuilder {
    var title: String? = null
        private set

    var description: String? = null
        private set

    private var url: String? = null

    private var color: Color? = null

    private var image: String? = null
    private var thumbnail: String? = null

    private var timestamp: Instant? = null

    private var author: Author? = null
    private var footer: Footer? = null
    private val fields: MutableList<Field> = mutableListOf()

    fun title(title: String): EmbedBuilder {
        this.title = title
        return this
    }

    fun title(title: String, url: String? = null): EmbedBuilder {
        this.title = title
        this.url = url
        return this
    }

    fun author(name: String, url: String? = null, iconUrl: String? = null): EmbedBuilder {
        this.author = Author(name, url, iconUrl)
        return this
    }

    fun footer(text: String, iconUrl: String? = null): EmbedBuilder {
        this.footer = Footer(text, iconUrl)
        return this
    }

    fun field(name: String, value: String, inline: Boolean = false): EmbedBuilder {
        this.fields.add(Field(name, value, inline))
        return this
    }

    fun description(description: String): EmbedBuilder {
        this.description = description
        return this
    }

    fun append(content: String): EmbedBuilder {
        this.description += content
        return this
    }

    fun appendln(content: String): EmbedBuilder {
        this.description += "$content\n"
        return this
    }

    fun mention(vararg target: Mentionable): EmbedBuilder {
        description += target.joinToString(" ") { it.mention }
        return this
    }

    fun mentionln(vararg target: Mentionable): EmbedBuilder {
        description += "${target.joinToString(" ") { it.mention }}\n"
        return this
    }

    fun mentionUser(vararg target: Long): EmbedBuilder {
        description += target.joinToString(" ") { "<@$it>" }
        return this
    }

    fun mentionMember(vararg target: Long): EmbedBuilder {
        description += target.joinToString(" ") { "<@!$it>" }
        return this
    }

    fun mentionChannel(vararg target: Long): EmbedBuilder {
        description += target.joinToString(" ") { "<#$it>" }
        return this
    }

    fun mentionRole(vararg target: Long): EmbedBuilder {
        description += target.joinToString(" ") { "<@&$it>" }
        return this
    }

    fun mentionEmoji(name: String, id: Long): EmbedBuilder {
        description += "<:$name:$id>"
        return this
    }

    fun mentionAnimatedEmoji(name: String, id: Long): EmbedBuilder {
        description += "<a:$name:$id>"
        return this
    }

    fun mentionUserln(vararg target: Long): EmbedBuilder {
        description += "${target.joinToString(" ") { "<@$it>" }}\n"
        return this
    }

    fun mentionMemberln(vararg target: Long): EmbedBuilder {
        description += "${target.joinToString(" ") { "<@!$it>" }}\n"
        return this
    }

    fun mentionChannelln(vararg target: Long): EmbedBuilder {
        description += "${target.joinToString(" ") { "<#$it>" }}\n"
        return this
    }

    fun mentionRoleln(vararg target: Long): EmbedBuilder {
        description += "${target.joinToString(" ") { "<@&$it>" }}\n"
        return this
    }

    fun mentionEmojiln(name: String, id: Long): EmbedBuilder {
        description += "<:$name:$id>\n"
        return this
    }

    fun mentionAnimatedEmojiln(name: String, id: Long): EmbedBuilder {
        description += "<a:$name:$id>\n"
        return this
    }

    operator fun String.unaryPlus(): EmbedBuilder {
        this@EmbedBuilder.description += "$this\n"
        return this@EmbedBuilder
    }

    fun color(color: Color): EmbedBuilder {
        this.color = color
        return this
    }

    fun image(imageUrl: String): EmbedBuilder {
        this.image = imageUrl
        return this
    }

    fun thumbnail(thumbnailUrl: String): EmbedBuilder {
        this.thumbnail = thumbnailUrl
        return this
    }

    fun timestamp(timestamp: Instant): EmbedBuilder {
        this.timestamp = timestamp
        return this
    }

    fun build(): Embed = EmbedImpl(
        title, url, author, description, fields, color, image, thumbnail, timestamp, footer
    )
}