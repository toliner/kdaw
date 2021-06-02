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

    @Volatile
    private var globalRateLimitEnds: Long = 0L
    private val mutex = Mutex()
    private val contexts = ConcurrentHashMap<Int, RateLimitContext>()

    suspend fun request(url: String, method: HttpMethod = HttpMethod.Get, data: JsonObject? = null): JsonObject? {
        repeat(50) {
            wait(url)

            try {
                Diskord.LOGGER.debug("Request: ${url}, method: $method, data: $data, retry: $it / 50")

                val response = client.request<HttpResponse>(url) {
                    this.method = method

                    if (method == HttpMethod.Get) {
                        if (data != null)
                            url(url + "?" + data.entries.joinToString("&") { "${it.key}=${it.value}" })

                    } else {
                        contentType(ContentType.Application.Json)
                        body = data?.toString() ?: "{}"
                    }
                }

                val contentType = response.contentType()
                val body = response.readText()

                val json = if (contentType == ContentType.Application.Json) {
                    body.asJsonObject()
                } else {
                    null
                }

                Diskord.LOGGER.debug("Response: ${response.status.value}, body: $json")

                val rateLimit = response.headers["X-RateLimit-Limit"]?.toInt()
                val rateLimitRemaining = response.headers["X-RateLimit-Remaining"]?.toInt()
                val rateLimitEnds = response.headers["X-RateLimit-Reset"]?.toLong()

                if (response.status.value !in 200..299) {
                    incrementRateLimitRemaining(url)
                }

                if (rateLimit != null && rateLimitRemaining != null && rateLimitEnds != null) {
                    setRateLimit(url, rateLimit)
                    setRateLimitRemaining(url, rateLimitRemaining)
                    setRateLimitEnds(url, rateLimitEnds * 1000)
                    Diskord.LOGGER.debug("RateLimit: $rateLimit, Remaining: $rateLimitRemaining, Ends: $rateLimitEnds")
                }

                if (response.status == HttpStatusCode.TooManyRequests) {
                    if (json == null) throw DiscordAPIException("API Rate limits exceeded.")

                    val delay = json.getLong("retry_after")

                    if (json.getBoolean("global")) {
                        setGlobalRateLimitEnds(delay)
                    } else {
                        setRateLimitEnds(url, System.currentTimeMillis() + delay)
                        setRateLimitRemaining(url, 0)
                    }

                    return@repeat
                }

                when (response.status) {
                    HttpStatusCode.BadRequest,
                    HttpStatusCode.Unauthorized,
                    HttpStatusCode.Forbidden,
                    HttpStatusCode.NotFound,
                    HttpStatusCode.MethodNotAllowed,
                    HttpStatusCode.BadGateway -> throw DiscordAPIException("An expected error: ${response.status}, Request: $method:$url: $data")

                    HttpStatusCode.InternalServerError,
                    HttpStatusCode.NotImplemented,
                    HttpStatusCode.ServiceUnavailable,
                    HttpStatusCode.GatewayTimeout,
                    HttpStatusCode.VersionNotSupported,
                    HttpStatusCode.VariantAlsoNegotiates,
                    HttpStatusCode.InsufficientStorage -> throw Exception("An unexpected error: ${response.status}, Request: $method:$url: $data")
                }

                return json
            } catch (e: DiscordAPIException) {
                throw e
            } catch (e: Exception) {
                Diskord.LOGGER.error(e.message)
            }
        }

        throw DiscordAPIException("Retry failed for 50 times.")
    }

    suspend fun setGlobalRateLimitEnds(delay: Long) = mutex.withLock {
        globalRateLimitEnds = System.currentTimeMillis() + delay
        Diskord.LOGGER.info("Hit global rate limit ($delay ms)")
    }

    suspend fun setRateLimitEnds(url: String, value: Long) = mutex.withLock {
        contexts[url.hashCode()] = contexts[url.hashCode()]?.apply {
            reset = maxOf(reset ?: 0, value)
        } ?: RateLimitContext(reset = value)
    }

    suspend fun setRateLimit(url: String, value: Int) = mutex.withLock {
        contexts[url.hashCode()] = contexts[url.hashCode()]?.apply {
            remaining = remaining?.minus(1)
            limit = value
        } ?: RateLimitContext(remaining = value - 1, limit = value)
    }

    suspend fun incrementRateLimitRemaining(url: String) = mutex.withLock {
        contexts[url.hashCode()]?.apply {
            remaining = remaining?.plus(1)
        }
    }

    suspend fun setRateLimitRemaining(url: String, value: Int) = mutex.withLock {
        contexts[url.hashCode()] = contexts[url.hashCode()]?.apply {
            remaining = value
        } ?: RateLimitContext(remaining = value)
    }

    suspend fun wait(endPoint: String) {
        mutex.withLock {
            if (globalRateLimitEnds > System.currentTimeMillis()) delay(globalRateLimitEnds - System.currentTimeMillis())
        }

        val hashCode = endPoint.hashCode()

        val context = contexts[hashCode] ?: return
        if (context.remaining ?: 0 > 0) return

        while (maxOf(globalRateLimitEnds, context.reset ?: 0) > System.currentTimeMillis()) {
            val delay = maxOf(globalRateLimitEnds, context.reset ?: 0) - System.currentTimeMillis()

            delay(delay)
        }

        context.remaining = context.limit!!
    }

    class DiscordAPIException(message: String) : Exception(message)
}