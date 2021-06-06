package dev.kotx.kdaw.entity.server.channel.announcement

import dev.kotx.kdaw.entity.server.channel.category.*
import dev.kotx.kdaw.entity.server.permission.*

class NewsChannelEditor(
    newsChannel: NewsChannel
) {
    internal var name: String = newsChannel.name
    internal var disableNewsChannel: Boolean = false
    internal var position: Int = newsChannel.position
    internal var topic: String? = newsChannel.topic
    internal var isNsfw: Boolean = newsChannel.isNsfw
    internal var memberPermissionOverwrites: List<MemberPermissionOverwrite> = newsChannel.memberPermissionOverwrites
    internal var rolePermissionOverwrites: List<RolePermissionOverwrite> = newsChannel.rolePermissionOverwrites
    internal var category: ServerCategory? = newsChannel.category
}