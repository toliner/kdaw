package dev.kotx.diskord.util

import kotlinx.serialization.*
import kotlinx.serialization.json.*

fun Any.asJsonString() = Json.encodeToString(this)

fun String.asJsonElement() = Json.parseToJsonElement(this)
fun String.asJsonObject() = Json.parseToJsonElement(this).jsonObject
fun String.asJsonArray() = Json.parseToJsonElement(this).jsonArray

fun Any.asJsonElement() = asJsonString().asJsonElement()
fun Any.asJsonObject() = asJsonString().asJsonObject()
fun Any.asJsonArray() = asJsonString().asJsonArray()

inline fun <reified T> String.parseToObject() = Json.decodeFromString<T>(this)

fun JsonObject.getString(key: String) = get(key)!!.jsonPrimitive.content
fun JsonObject.getStringOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.contentOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getInt(key: String) = get(key)!!.jsonPrimitive.int
fun JsonObject.getIntOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.intOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getLong(key: String) = get(key)!!.jsonPrimitive.long
fun JsonObject.getLongOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.longOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getDouble(key: String) = get(key)!!.jsonPrimitive.double
fun JsonObject.getDoubleOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.doubleOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getFloat(key: String) = get(key)!!.jsonPrimitive.float
fun JsonObject.getFloatOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.floatOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getBoolean(key: String) = get(key)!!.jsonPrimitive.boolean
fun JsonObject.getBooleanOrNull(key: String) = try {
    get(key)?.jsonPrimitive?.booleanOrNull
} catch (e: Exception) {
    null
}

fun JsonObject.getPrimitive(key: String) = get(key)!!.jsonPrimitive
fun JsonObject.getPrimitiveOrNull(key: String) = try {
    get(key)?.jsonPrimitive
} catch (e: Exception) {
    null
}

fun JsonObject.getObject(key: String) = get(key)!!.jsonObject
fun JsonObject.getObjectOrNull(key: String) = try {
    get(key)?.jsonObject
} catch (e: Exception) {
    null
}

fun JsonObject.getArray(key: String) = get(key)!!.jsonArray
fun JsonObject.getArrayOrNull(key: String) = try {
    get(key)?.jsonArray
} catch (e: Exception) {
    null
}

fun JsonElement.getString(key: String) = jsonObject[key]!!.jsonPrimitive.content
fun JsonElement.getStringOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.contentOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getInt(key: String) = jsonObject[key]!!.jsonPrimitive.int
fun JsonElement.getIntOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.intOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getLong(key: String) = jsonObject[key]!!.jsonPrimitive.long
fun JsonElement.getLongOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.longOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getDouble(key: String) = jsonObject[key]!!.jsonPrimitive.double
fun JsonElement.getDoubleOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.doubleOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getFloat(key: String) = jsonObject[key]!!.jsonPrimitive.float
fun JsonElement.getFloatOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.floatOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getBoolean(key: String) = jsonObject[key]!!.jsonPrimitive.boolean
fun JsonElement.getBooleanOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive?.booleanOrNull
} catch (e: Exception) {
    null
}

fun JsonElement.getPrimitive(key: String) = jsonObject[key]!!.jsonPrimitive
fun JsonElement.getPrimitiveOrNull(key: String) = try {
    jsonObject[key]?.jsonPrimitive
} catch (e: Exception) {
    null
}

fun JsonElement.getObject(key: String) = jsonObject[key]!!.jsonObject
fun JsonElement.getObjectOrNull(key: String) = try {
    jsonObject[key]?.jsonObject
} catch (e: Exception) {
    null
}

fun JsonElement.getArray(key: String) = jsonObject[key]!!.jsonArray
fun JsonElement.getArrayOrNull(key: String) = try {
    jsonObject[key]?.jsonArray
} catch (e: Exception) {
    null
}