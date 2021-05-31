package dev.kotx.diskord.rest

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.*
import java.util.concurrent.*

class RateLimit {
    class Config
    companion object Feature : HttpClientFeature<Config, RateLimit> {
        private val limits = ConcurrentHashMap<Int, Context>()

        override val key: AttributeKey<RateLimit> = AttributeKey("RateLimiter")
        override fun prepare(block: Config.() -> Unit): RateLimit = RateLimit()

        override fun install(feature: RateLimit, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Send) {
                val hash = context.url.encodedPath.hashCode()

                if (limits[hash] == null) {
                    proceed()
                    return@intercept
                }

                if (limits[hash]!!.remaining < 1) {
                    delay(limits[hash]!!.reset - System.currentTimeMillis())
                    proceed()
                    return@intercept
                }

                proceed()
            }

            scope.receivePipeline.intercept(HttpReceivePipeline.After) {
                val headers = context.response.headers

                val limit = headers["X-RateLimit-Limit"]?.toInt()
                val remaining = headers["X-RateLimit-Remaining"]?.toInt()
                val reset = headers["X-RateLimit-Reset"]?.toLong()

                if (limit != null && remaining != null && reset != null) {
                    val hash = context.request.url.encodedPath.hashCode()
                    limits[hash] = Context(limit, remaining, reset)
                }

                proceed()
            }
        }
    }

    private class Context(
        val limit: Int,
        val remaining: Int,
        val reset: Long
    )
}