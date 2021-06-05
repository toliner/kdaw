package dev.kotx.kdaw.entity

import dev.kotx.kdaw.*
import java.time.*

interface Entity {
    val kdaw: Kdaw
    val id: Long
    val timestamp: Instant
        get() = Instant.ofEpochMilli((id ushr 22) + 1420070400000L)
}