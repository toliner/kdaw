package dev.kotx.kdaw.entity.message.embed

import dev.kotx.kdaw.util.*
import kotlinx.serialization.json.*
import java.awt.*
import java.time.*
import java.time.format.*

class EmbedImpl(
    override val title: String?,
    override val url: String?,
    override val author: Author?,
    override val description: String?,
    override val fields: List<Field>?,
    override val color: Color?,
    override val image: String?,
    override val thumbnail: String?,
    override val timestamp: Instant?,
    override val footer: Footer?
) : Embed {
    override fun parse(): JsonElement = json {
        title?.also { "title" to it }
        "type" to "rich"
        description?.also { "description" to it }
        url?.also { "url" to it }
        timestamp?.also { "timestamp" to DateTimeFormatter.ISO_DATE_TIME.format(it) }
        color?.also { "color" to color.rgb }
        footer?.also {
            "footer" to json {
                it.text?.also { "text" to it }
                it.iconUrl?.also { "icon_url" to it }
            }
        }
        image?.also { "image" to it }
        thumbnail?.also { "thumbnail" to it }
        author?.also {
            "author" to json {
                it.name?.also { "name" to it }
                it.url?.also { "url" to it }
                it.iconUrl?.also { "icon_url" to it }
            }
        }
        fields?.also {
            "fields" to jsonArray {
                it.forEach {
                    +json {
                        it.name?.also { "name" to it }
                        it.value?.also { "value" to it }
                        "inline" to it.inline
                    }
                }
            }
        }
    }
}