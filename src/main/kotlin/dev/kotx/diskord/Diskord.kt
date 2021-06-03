package dev.kotx.diskord

import org.slf4j.*

abstract class Diskord(
    val token: String
) {
    companion object {
        internal val LOGGER = LoggerFactory.getLogger("Diskord")

        fun create(token: String, block: DiskordBuilder.() -> Unit): Diskord = DiskordBuilder(token).apply(block).build()
    }
}