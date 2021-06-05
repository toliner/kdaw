package dev.kotx.kdaw.entity.server

enum class UserStatus(
    val id: String
) {
    IDLE("idle"),
    DO_NOT_DISTURB("dnd"),
    ONLINE("online"),
    OFFLINE("offline"),

    UNKNOWN("unknown");

    companion object {
        operator fun get(id: String) = values().find { it.id == id } ?: UNKNOWN
    }
}
