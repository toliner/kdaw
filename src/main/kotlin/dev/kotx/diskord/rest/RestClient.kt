package dev.kotx.diskord.rest

import dev.kotx.diskord.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

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

        Json {
            serializer = KotlinxSerializer()
        }

        install(RateLimit)
    }
}