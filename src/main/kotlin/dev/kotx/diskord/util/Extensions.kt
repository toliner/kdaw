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
fun JsonObject.getStringOrNull(key: String) = get(key)?.jsonPrimitive?.contentOrNull

fun JsonObject.getInt(key: String) = get(key)!!.jsonPrimitive.int
fun JsonObject.getIntOrNull(key: String) = get(key)?.jsonPrimitive?.intOrNull

fun JsonObject.getLong(key: String) = get(key)!!.jsonPrimitive.long
fun JsonObject.getLongOrNull(key: String) = get(key)?.jsonPrimitive?.longOrNull

fun JsonObject.getDouble(key: String) = get(key)!!.jsonPrimitive.double
fun JsonObject.getDoubleOrNull(key: String) = get(key)?.jsonPrimitive?.doubleOrNull

fun JsonObject.getFloat(key: String) = get(key)!!.jsonPrimitive.float
fun JsonObject.getFloatOrNull(key: String) = get(key)?.jsonPrimitive?.floatOrNull

fun JsonObject.getBoolean(key: String) = get(key)!!.jsonPrimitive.boolean
fun JsonObject.getBooleanOrNull(key: String) = get(key)?.jsonPrimitive?.booleanOrNull

fun JsonObject.getPrimitive(key: String) = get(key)!!.jsonPrimitive
fun JsonObject.getPrimitiveOrNull(key: String) = get(key)?.jsonPrimitive

fun JsonObject.getObject(key: String) = get(key)!!.jsonObject
fun JsonObject.getObjectOrNull(key: String) = get(key)?.jsonObject

fun JsonObject.getArray(key: String) = get(key)!!.jsonArray
fun JsonObject.getArrayOrNull(key: String) = get(key)?.jsonArray

fun JsonElement.getString(key: String) = jsonObject[key]!!.jsonPrimitive.content
fun JsonElement.getStringOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.contentOrNull

fun JsonElement.getInt(key: String) = jsonObject[key]!!.jsonPrimitive.int
fun JsonElement.getIntOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.intOrNull

fun JsonElement.getLong(key: String) = jsonObject[key]!!.jsonPrimitive.long
fun JsonElement.getLongOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.longOrNull

fun JsonElement.getDouble(key: String) = jsonObject[key]!!.jsonPrimitive.double
fun JsonElement.getDoubleOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.doubleOrNull

fun JsonElement.getFloat(key: String) = jsonObject[key]!!.jsonPrimitive.float
fun JsonElement.getFloatOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.floatOrNull

fun JsonElement.getBoolean(key: String) = jsonObject[key]!!.jsonPrimitive.boolean
fun JsonElement.getBooleanOrNull(key: String) = jsonObject[key]?.jsonPrimitive?.booleanOrNull

fun JsonElement.getPrimitive(key: String) = jsonObject[key]!!.jsonPrimitive
fun JsonElement.getPrimitiveOrNull(key: String) = jsonObject[key]?.jsonPrimitive

fun JsonElement.getObject(key: String) = jsonObject[key]!!.jsonObject
fun JsonElement.getObjectOrNull(key: String) = jsonObject[key]?.jsonObject

fun JsonElement.getArray(key: String) = jsonObject[key]!!.jsonArray
fun JsonElement.getArrayOrNull(key: String) = jsonObject[key]?.jsonArray