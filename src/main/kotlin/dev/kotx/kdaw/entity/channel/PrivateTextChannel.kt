package dev.kotx.kdaw.entity.channel

import dev.kotx.kdaw.entity.user.*

interface PrivateTextChannel: TextChannel {
    val recipients: List<User>
    val owner: User?
}