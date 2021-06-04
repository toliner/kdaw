package dev.kotx.diskord

import dev.kotx.diskord.rest.*

class DiskordImpl(token: String) : Diskord(token) {
    private val restClient = RestClient(this)
}