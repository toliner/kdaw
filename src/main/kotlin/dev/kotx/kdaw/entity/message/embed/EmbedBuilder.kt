package dev.kotx.kdaw.entity.message.embed

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
        if (this.description == null) this.description = ""
        this.description += content
        return this
    }

    fun appendln(content: String): EmbedBuilder {
        append("$content\n")
        return this
    }

    operator fun String.unaryPlus(): EmbedBuilder {
        appendln(this)
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