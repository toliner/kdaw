package dev.kotx.diskord.rest

class RateLimitContext(
    var limit: Int? = null,
    var remaining: Int? = null,
    var reset: Long? = null
)