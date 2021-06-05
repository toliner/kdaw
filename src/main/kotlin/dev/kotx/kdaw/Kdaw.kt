package dev.kotx.kdaw

import io.ktor.client.*
import org.slf4j.*

abstract class Kdaw(
    val token: String
) {
    abstract suspend fun test()

    companion object {
        internal val LOGGER = LoggerFactory.getLogger("Kdaw")
        internal const val ENDPOINT = "https://discord.com/api/v9"
        internal val HTTP_CLIENT = HttpClient()

        fun create(token: String, block: KdawBuilder.() -> Unit = {}): Kdaw = KdawBuilder(token).apply(block).build()
    }
}