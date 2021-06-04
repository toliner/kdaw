package dev.kotx.kdaw.rest

import dev.kotx.kdaw.*
import dev.kotx.kdaw.util.*
import dev.kotx.kdaw.util.JsonBuilder
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

class RestClient(
    private val kdaw: KdawImpl
) {
    private val client = HttpClient {
        expectSuccess = false

        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.UserAgent, "DiscordBot (https://github.com/Kotlin-Chan/kdaw, development)")
            header(HttpHeaders.Authorization, "Bot ${kdaw.token}")
        }
    }

    suspend fun request(
        endPoint: EndPoint,
        data: JsonBuilder.() -> Unit = {}
    ): JsonObject? {
        val response = client.request<HttpStatement>(Kdaw.ENDPOINT + endPoint.url) {
            method = endPoint.method

            val json = JsonBuilder().apply(data).build()

            if (endPoint.method == HttpMethod.Get) {
                json.entries.filter { it.value is JsonPrimitive }.forEach { parameter(it.key, it.value.toString()) }
            } else {
                contentType(ContentType.Application.Json)
                body = json.toString()
            }
        }.execute()

        return try {
            response.readText().asJsonObject()
        } catch (e: Exception) {
            null
        }
    }
}