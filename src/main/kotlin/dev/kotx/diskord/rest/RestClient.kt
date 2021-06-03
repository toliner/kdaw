package dev.kotx.diskord.rest

import dev.kotx.diskord.*
import dev.kotx.diskord.util.JsonBuilder
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.*

class RestClient(
    private val diskord: DiskordImpl
) {
    private val client = HttpClient {
        expectSuccess = false

        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.Authorization, "Bot ${diskord.token}")
            header(HttpHeaders.UserAgent, "DiscordBot (https://github.com/Kotlin-Chan/diskord, development)")
        }
    }

    suspend fun request(
        endPoint: EndPoint,
        data: JsonBuilder.() -> Unit = {}
    ) {
        val response = client.request<HttpStatement>(Diskord.ENDPOINT + endPoint.url) {
            method = endPoint.method

            val json = JsonBuilder().apply(data).build()

            if (endPoint.method == HttpMethod.Get)
                json.entries.filter { it.value is JsonPrimitive }.forEach { parameter(it.key, it.value.toString()) }
            else
                body = json.toString()
        }
    }
}