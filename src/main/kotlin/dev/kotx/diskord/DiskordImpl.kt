package dev.kotx.diskord

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class DiskordImpl(token: String) : Diskord(token) {
    private val client = HttpClient {
        expectSuccess = false
        defaultRequest {
            header(HttpHeaders.Accept, "application/json")
            header(HttpHeaders.Authorization, "Bot $token")
            header(HttpHeaders.UserAgent, "DiscordBot (https://github.com/Kotlin-Chan/diskord, development)")
        }

        install("RateLimit") {
            receivePipeline.intercept(HttpReceivePipeline.Before) {
                val headers = context.response.headers
                val limit = headers["X-RateLimit-Limit"]?.toInt()
                val remaining = headers["X-RateLimit-Remaining"]?.toInt()
                val reset = headers["X-RateLimit-Reset"]?.toLong()
            }
        }
    }
}