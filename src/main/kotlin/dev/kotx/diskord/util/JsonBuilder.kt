package dev.kotx.diskord.util

import kotlinx.serialization.json.*

class JsonBuilder {
    private var content = mutableMapOf<String, JsonElement>()

    infix fun String.to(value: String?) {
        content[this] = JsonPrimitive(value)
    }

    infix fun String.to(value: Number?) {
        content[this] = JsonPrimitive(value)
    }

    infix fun String.to(value: Boolean?) {
        content[this] = JsonPrimitive(value)
    }

    infix fun String.to(value: JsonElement) {
        content[this] = value
    }

    infix fun String.to(builder: JsonBuilder.() -> Unit) {
        content[this] = JsonBuilder().apply(builder).build()
    }

    infix fun String.array(builder: JsonArrayBuilder.() -> Unit) {
        content[this] = JsonArrayBuilder().apply(builder).build()
    }

    fun build() = JsonObject(content)
}

class JsonArrayBuilder {
    private var contents = mutableListOf<JsonElement>()

    operator fun String?.unaryPlus() {
        contents.add(JsonPrimitive(this))
    }

    operator fun Number?.unaryPlus() {
        contents.add(JsonPrimitive(this))
    }

    operator fun JsonElement.unaryPlus() {
        contents.add(this)
    }

    fun build() = JsonArray(contents)
}

fun json(block: JsonBuilder.() -> Unit) = JsonBuilder().apply(block).build()
fun jsonArray(block: JsonArrayBuilder.() -> Unit) = JsonArrayBuilder().apply(block).build()