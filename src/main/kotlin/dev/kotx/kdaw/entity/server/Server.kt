package dev.kotx.kdaw.entity.server

import dev.kotx.kdaw.entity.*
import dev.kotx.kdaw.entity.image.*
import dev.kotx.kdaw.entity.server.channel.*
import dev.kotx.kdaw.entity.server.channel.announcement.*
import dev.kotx.kdaw.entity.server.channel.category.*
import dev.kotx.kdaw.entity.server.channel.store.*
import dev.kotx.kdaw.entity.server.channel.text.*
import dev.kotx.kdaw.entity.server.channel.voice.*
import dev.kotx.kdaw.entity.server.emoji.*
import javax.management.relation.*

interface Server: Nameable, Entity {
    val ready: Boolean

    val memberCount: Int

    val isOwner: Boolean

    val ownerId: Long

    val icon: Image?

    val splash: Image?

    val discoverySplash: Image?

    val region: Region

    val afkChannel: ServerVoiceChannel?

    val afkTimeout: Int?

    val verificationLevel: VerificationLevel

    val messageNotificationLevel: MessageNotificationLevel

    val explicitContentFilterLevel: ExplicitContentFilterLevel

    val mfaLevel: MfaLevel

    val roles: List<Role>

    val emojis: List<Emoji>

    val textChannels: List<ServerTextChannel>

    val voiceChannels: List<ServerVoiceChannel>

    val categories: List<ServerCategory>

    val announcementChannels: List<AnnouncementChannel>

    val storeChannels: List<StoreChannel>

    val channels: List<ServerChannel>
}