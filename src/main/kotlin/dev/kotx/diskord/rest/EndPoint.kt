package dev.kotx.diskord.rest

import io.ktor.http.*

open class EndPoint(
    val method: HttpMethod,
    val url: String
) {
    class AuditLog {
        class Get(guildId: Long) : EndPoint(HttpMethod.Get, "/guilds/$guildId/audit-logs")
    }

    open class Channel(method: HttpMethod, channelId: Long, path: String = "") : EndPoint(method, "/channels/$channelId$path") {
        class Get(channelId: Long) : Channel(HttpMethod.Get, channelId)
        class Modify(channelId: Long) : Channel(HttpMethod.Patch, channelId)
        class Delete(channelId: Long) : Channel(HttpMethod.Delete, channelId)
        class Follow(channelId: Long) : Channel(HttpMethod.Post, channelId, "/followers")
        class Typing(channelId: Long) : Channel(HttpMethod.Post, channelId, "/typing")

        open class Pin(method: HttpMethod, channelId: Long, path: String = "") : Channel(method, channelId, "/pins$path") {
            class List(channelId: Long) : Pin(HttpMethod.Get, channelId)
            class Create(channelId: Long) : Pin(HttpMethod.Put, channelId)
            class Delete(channelId: Long, messageId: Long) : Pin(HttpMethod.Delete, channelId, "/$messageId")
        }

        open class Recipient(method: HttpMethod, channelId: Long, userId: Long) : Channel(method, channelId, "/recipients/$userId") {
            class Add(channelId: Long, userId: Long) : Recipient(HttpMethod.Put, channelId, userId)
            class Delete(channelId: Long, userId: Long) : Recipient(HttpMethod.Delete, channelId, userId)
        }

        open class Invite(method: HttpMethod, channelId: Long) : Channel(method, channelId, "/invites") {
            class List(channelId: Long) : Invite(HttpMethod.Get, channelId)
            class Create(channelId: Long) : Invite(HttpMethod.Post, channelId)
        }

        open class Permission(method: HttpMethod, channelId: Long, path: String = "") : Channel(method, channelId, "/permissions$path") {
            class Edit(channelId: Long, targetId: Long) : Permission(HttpMethod.Put, channelId, "/$targetId")
            class Delete(channelId: Long, targetId: Long) : Permission(HttpMethod.Delete, channelId, "/$targetId")
        }

        open class Thread(method: HttpMethod, channelId: Long, path: String = "") : Channel(method, channelId, path) {
            class StartWithMessage(channelId: Long, messageId: Long) : Thread(HttpMethod.Post, channelId, "/messages/$messageId/threads")
            class StartWithoutMessage(channelId: Long) : Thread(HttpMethod.Post, channelId, "/threads")
            class ListActive(channelId: Long): Thread(HttpMethod.Get, channelId, "/threads/active")
            class ListPublicArchived(channelId: Long): Thread(HttpMethod.Get, channelId, "/threads/archived/public")
            class ListPrivateArchived(channelId: Long): Thread(HttpMethod.Get, channelId, "/threads/archived/private")
            class ListJoinedPrivateArchived(channelId: Long): Thread(HttpMethod.Get, channelId, "/users/@me/threads/archived/private")

            open class Members(method: HttpMethod, channelId: Long, path: String = "") : Thread(method, channelId, "/thread-members$path") {
                class Join(channelId: Long) : Members(HttpMethod.Put, channelId, "/@me")
                class Add(channelId: Long, userId: Long) : Members(HttpMethod.Put, channelId, "/$userId")
                class Leave(channelId: Long) : Members(HttpMethod.Delete, channelId, "/@me")
                class Remove(channelId: Long, userId: Long) : Members(HttpMethod.Delete, channelId, "/$userId")
                class ListMember(channelId: Long) : Members(HttpMethod.Get, channelId)
            }
        }

        open class Message(method: HttpMethod, channelId: Long, path: String = "") : Channel(method, channelId, "/messages$path") {
            class List(channelId: Long) : Message(HttpMethod.Get, channelId)
            class Get(channelId: Long, messageId: Long) : Message(HttpMethod.Get, channelId, "/$messageId")
            class Create(channelId: Long) : Message(HttpMethod.Post, channelId)
            class Crosspost(channelId: Long, messageId: Long) : Message(HttpMethod.Post, channelId, "/$messageId/crosspost")
            class Edit(channelId: Long, messageId: Long) : Message(HttpMethod.Patch, channelId, "/$messageId")
            class Delete(channelId: Long, messageId: Long) : Message(HttpMethod.Delete, channelId, "/$messageId")
            class BulkDelete(channelId: Long) : Message(HttpMethod.Post, channelId, "/bulk-delete")

            open class Reaction(method: HttpMethod, channelId: Long, messageId: Long, path: String = "") : Message(method, channelId, "/$messageId/reactions$path") {
                class List(channelId: Long, messageId: Long, emoji: String) : Reaction(HttpMethod.Get, channelId, messageId, "/$emoji")
                class Create(channelId: Long, messageId: Long, emoji: String) : Reaction(HttpMethod.Put, channelId, messageId, "/$emoji/@me")
                class DeleteOwn(channelId: Long, messageId: Long, emoji: String) : Reaction(HttpMethod.Delete, channelId, messageId, "/$emoji/@me")
                class DeleteOthers(channelId: Long, messageId: Long, emoji: String, userId: Long) : Reaction(HttpMethod.Delete, channelId, messageId, "/$emoji/$userId")
                class DeleteAll(channelId: Long, messageId: Long) : Reaction(HttpMethod.Delete, channelId, messageId)
                class DeleteAllForEmoji(channelId: Long, messageId: Long, emoji: String) : Reaction(HttpMethod.Delete, channelId, messageId, "/$emoji")
            }
        }
    }

    class Emoji
    class Guild
    class GuildTemplate
    class Invite
    class StageInstance
    class User
    class Voice
    class Webhook
}