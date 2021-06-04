package dev.kotx.diskord.gateway

enum class GatewayIntent(val decimal: Int) {
    GUILDS(1),
    GUILD_MEMBERS(2),
    GUILD_BANS(4),
    GUILD_EMOJIS(8),
    GUILD_INTEGRATIONS(16),
    GUILD_WEBHOOKS(32),
    GUILD_INVITES(64),
    GUILD_VOICE_STATES(128),
    GUILD_PRESENCES(256),
    GUILD_MESSAGES(512),
    GUILD_MESSAGE_REACTIONS(1024),
    GUILD_MESSAGE_TYPING(2048),
}