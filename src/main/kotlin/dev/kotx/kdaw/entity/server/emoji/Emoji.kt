package dev.kotx.kdaw.entity.server.emoji

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.image.*
import dev.kotx.kdaw.entity.server.*

interface Emoji: Nameable, Entity {
    val server: Server

    val image: Image
}