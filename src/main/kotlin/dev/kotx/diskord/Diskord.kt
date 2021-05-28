package dev.kotx.diskord

class Diskord(
    val token: String
) {

    class Builder(
        private val token: String
    ) {

        fun build(): Diskord {
            return Diskord(token)
        }
    }

    companion object {
        fun create(token: String, block: Builder.() -> Unit): Diskord = Builder(token).apply(block).build()
    }
}