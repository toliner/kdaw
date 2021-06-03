package dev.kotx.diskord.rest

import dev.kotx.diskord.*
import dev.kotx.diskord.util.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import kotlinx.serialization.json.*
import java.util.concurrent.*

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

    suspend fun request(url: String, method: HttpMethod = HttpMethod.Get, data: JsonObject? = null) {

    }
}