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
            class ListActive(channelId: Long) : Thread(HttpMethod.Get, channelId, "/threads/active")
            class ListPublicArchived(channelId: Long) : Thread(HttpMethod.Get, channelId, "/threads/archived/public")
            class ListPrivateArchived(channelId: Long) : Thread(HttpMethod.Get, channelId, "/threads/archived/private")
            class ListJoinedPrivateArchived(channelId: Long) : Thread(HttpMethod.Get, channelId, "/users/@me/threads/archived/private")

            open class Members(method: HttpMethod, channelId: Long, path: String = "") : Thread(method, channelId, "/thread-members$path") {
                class Join(channelId: Long) : Members(HttpMethod.Put, channelId, "/@me")
                class Add(channelId: Long, userId: Long) : Members(HttpMethod.Put, channelId, "/$userId")
                class Leave(channelId: Long) : Members(HttpMethod.Delete, channelId, "/@me")
                class Remove(channelId: Long, userId: Long) : Members(HttpMethod.Delete, channelId, "/$userId")
                class List(channelId: Long) : Members(HttpMethod.Get, channelId)
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

    open class Emoji(method: HttpMethod, guildId: Long, path: String = "") : EndPoint(method, "/guilds/$guildId/emojis$path") {
        class List(guildId: Long) : Emoji(HttpMethod.Get, guildId)
        class Get(guildId: Long, emojiId: Long) : Emoji(HttpMethod.Get, guildId, "/$emojiId")
        class Create(guildId: Long) : Emoji(HttpMethod.Post, guildId)
        class Modify(guildId: Long, emojiId: Long) : Emoji(HttpMethod.Patch, guildId, "/$emojiId")
        class Delete(guildId: Long, emojiId: Long) : Emoji(HttpMethod.Delete, guildId, "/$emojiId")
    }

    open class Guild(method: HttpMethod, path: String = "") : EndPoint(method, "/guilds$path") {
        class Create : Guild(HttpMethod.Post)
        class Get(guildId: Long) : Guild(HttpMethod.Get, "/$guildId")
        class GetPreview(guildId: Long) : Guild(HttpMethod.Get, "/$guildId/preview")
        class Modify(guildId: Long) : Guild(HttpMethod.Patch, "/$guildId")
        class Delete(guildId: Long) : Guild(HttpMethod.Delete, "/$guildId")
        class ListRegion(guildId: Long) : Guild(HttpMethod.Get, "/$guildId/regions")
        class ListInvite(guildId: Long) : Guild(HttpMethod.Get, "/$guildId/invites")
        class GetVanityUrl(guildId: Long) : Guild(HttpMethod.Get, "/$guildId/vanity-url")

        open class WelcomeScreen(method: HttpMethod, guildId: Long) : Guild(method, "/$guildId/welcome-screen") {
            class Get(guildId: Long) : WelcomeScreen(HttpMethod.Get, guildId)
            class Modify(guildId: Long) : WelcomeScreen(HttpMethod.Patch, guildId)
        }

        open class VoiceState(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId/voice-states$path") {
            class Update(guildId: Long) : VoiceState(HttpMethod.Patch, guildId, "/@me")
            class UpdateOwn(guildId: Long, userId: Long) : VoiceState(HttpMethod.Get, guildId, "/$userId")
        }

        open class Integration(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId/integrations$path") {
            class List(guildId: Long) : Integration(HttpMethod.Get, guildId)
            class Delete(guildId: Long, integrationId: Long) : Integration(HttpMethod.Delete, guildId, "/$integrationId")
        }

        open class Widget(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId$path") {
            class Get(guildId: Long) : Widget(HttpMethod.Get, guildId, "/widget.json")
            class GetSetting(guildId: Long) : Widget(HttpMethod.Get, guildId, "/widget")
            class GetImage(guildId: Long) : Widget(HttpMethod.Get, guildId, "/widget.png")
            class Modify(guildId: Long) : Widget(HttpMethod.Patch, guildId, "/widget")
        }

        open class Channel(method: HttpMethod, guildId: Long) : Guild(method, "/$guildId/channels") {
            class List(guildId: Long) : Channel(HttpMethod.Get, guildId)
            class Create(guildId: Long) : Channel(HttpMethod.Post, guildId)
            class ModifyPosition(guildId: Long) : Channel(HttpMethod.Patch, guildId)
        }

        open class Member(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId/members$path") {
            class Get(guildId: Long, userId: Long) : Member(HttpMethod.Get, guildId, "/$userId")
            class List(guildId: Long) : Member(HttpMethod.Get, guildId)
            class Search(guildId: Long) : Member(HttpMethod.Get, guildId, "/search")
            class Add(guildId: Long, userId: Long) : Member(HttpMethod.Get, guildId, "/$userId")
            class Remove(guildId: Long, userId: Long) : Member(HttpMethod.Delete, guildId, "/$userId")
            class Modify(guildId: Long, userId: Long) : Member(HttpMethod.Patch, guildId, "/$userId")
            class ModifyOwnNick(guildId: Long) : Member(HttpMethod.Patch, guildId, "/@me/nick")

            open class Role(method: HttpMethod, guildId: Long, userId: Long, roleId: Long) : Member(method, guildId, "/$userId/roles/$roleId") {
                class Add(guildId: Long, userId: Long, roleId: Long) : Role(HttpMethod.Put, guildId, userId, roleId)
                class Remove(guildId: Long, userId: Long, roleId: Long) : Role(HttpMethod.Delete, guildId, userId, roleId)
            }
        }

        open class Ban(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId/bans$path") {
            class List(guildId: Long) : Ban(HttpMethod.Get, guildId)
            class Get(guildId: Long, userId: Long) : Ban(HttpMethod.Get, guildId, "/$userId")
            class Create(guildId: Long, userId: Long) : Ban(HttpMethod.Put, guildId, "/$userId")
            class Remove(guildId: Long, userId: Long) : Ban(HttpMethod.Delete, guildId, "/$userId")
        }

        open class Role(method: HttpMethod, guildId: Long, path: String = "") : Guild(method, "/$guildId/roles$path") {
            class List(guildId: Long) : Role(HttpMethod.Get, guildId)
            class Create(guildId: Long) : Role(HttpMethod.Post, guildId)
            class ModifyPosition(guildId: Long) : Role(HttpMethod.Patch, guildId)
            class Modify(guildId: Long, roleId: Long) : Role(HttpMethod.Patch, guildId, "/$roleId")
            class Delete(guildId: Long, roleId: Long) : Role(HttpMethod.Delete, guildId, "/$roleId")
        }

        open class Prune(method: HttpMethod, guildId: Long) : Guild(method, "/$guildId/prune") {
            class Get(guildId: Long) : Prune(HttpMethod.Get, guildId)
            class Begin(guildId: Long) : Prune(HttpMethod.Post, guildId)
        }

        open class Template(method: HttpMethod, path: String = "") : Guild(method, path) {
            class Get(templateCode: String) : Template(HttpMethod.Get, "/templates/$templateCode")
            class CreateGuildFromCode(templateCode: String) : Template(HttpMethod.Post, "/templates/$templateCode")
            class List(guildId: Long) : Template(HttpMethod.Get, "/$guildId/templates")
            class Create(guildId: Long) : Template(HttpMethod.Post, "/$guildId/templates")
            class Sync(guildId: Long, templateCode: String) : Template(HttpMethod.Put, "/$guildId/templates/$templateCode")
            class Modify(guildId: Long, templateCode: String) : Template(HttpMethod.Patch, "/$guildId/templates/$templateCode")
            class Delete(guildId: Long, templateCode: String) : Template(HttpMethod.Delete, "/$guildId/templates/$templateCode")
        }
    }

    open class Invite(method: HttpMethod, inviteCode: String) : EndPoint(method, "/invites/$inviteCode") {
        class Get(inviteCode: String) : Invite(HttpMethod.Get, inviteCode)
        class Delete(inviteCode: String) : Invite(HttpMethod.Delete, inviteCode)
    }

    open class StageInstance(method: HttpMethod, path: String = "") : EndPoint(method, "/stage-instances$path") {
        class Create : StageInstance(HttpMethod.Post)
        class Get(channelId: Long) : StageInstance(HttpMethod.Get, "/$channelId")
        class Update(channelId: Long) : StageInstance(HttpMethod.Patch, "/$channelId")
        class Delete(channelId: Long) : StageInstance(HttpMethod.Delete, "/$channelId")
    }

    open class User(method: HttpMethod, path: String = "") : EndPoint(method, "/users$path") {
        class GetOwn : User(HttpMethod.Get, "/@me")
        class Get(userId: Long) : User(HttpMethod.Get, "/$userId")
        class ModifyOwn : User(HttpMethod.Patch, "/@me")
        class OwnGuilds : User(HttpMethod.Get, "/@me/guilds")
        class LeaveGuild(guildId: Long) : User(HttpMethod.Delete, "/@me/guilds/$guildId")
        class CreateDM : User(HttpMethod.Post, "/@me/channels")
        class GetConnection : User(HttpMethod.Get, "/@me/connections")
    }

    class Voice {
        class ListRegion : EndPoint(HttpMethod.Get, "/voice/regions")
    }

    class Webhook {
        class Create(channelId: Long) : EndPoint(HttpMethod.Post, "/channels/$channelId/webhooks")
        class ListChannel(channelId: Long) : EndPoint(HttpMethod.Get, "/channels/$channelId/webhooks")
        class ListGuild(guildId: Long) : EndPoint(HttpMethod.Get, "/guilds/$guildId/webhooks")
        class Get(webhookId: Long) : EndPoint(HttpMethod.Get, "/webhooks/$webhookId")
        class GetWithToken(webhookId: Long, token: String) : EndPoint(HttpMethod.Get, "/webhooks/$webhookId/$token")
        class Modify(webhookId: Long) : EndPoint(HttpMethod.Patch, "/webhooks/$webhookId")
        class ModifyWithToken(webhookId: Long, token: String) : EndPoint(HttpMethod.Patch, "/webhooks/$webhookId/$token")
        class Delete(webhookId: Long) : EndPoint(HttpMethod.Delete, "/webhooks/$webhookId")
        class DeleteWithToken(webhookId: Long, token: String) : EndPoint(HttpMethod.Delete, "/webhooks/$webhookId/$token")
        class Execute(webhookId: Long, token: String) : EndPoint(HttpMethod.Post, "/webhooks/$webhookId/$token")
        class ExecuteSlack(webhookId: Long, token: String) : EndPoint(HttpMethod.Post, "/webhooks/$webhookId/$token/slack")
        class ExecuteGithub(webhookId: Long, token: String) : EndPoint(HttpMethod.Post, "/webhooks/$webhookId/$token/github")
        class GetMessage(webhookId: Long, token: String, messageId: Long) : EndPoint(HttpMethod.Get, "/webhooks/$webhookId/$token/messages/$messageId")
        class EditMessage(webhookId: Long, token: String, messageId: Long) : EndPoint(HttpMethod.Patch, "/webhooks/$webhookId/$token/messages/$messageId")
        class DeleteMessage(webhookId: Long, token: String, messageId: Long) : EndPoint(HttpMethod.Delete, "/webhooks/$webhookId/$token/messages/$messageId")
    }
}
