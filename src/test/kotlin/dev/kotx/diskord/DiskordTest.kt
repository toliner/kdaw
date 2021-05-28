package dev.kotx.diskord

class DiskordTest {
    fun launchTest() {
        val diskord = Diskord.create("<< insert bot token here >>") {
            /**
             * listen<ServerMessageCreateEvent> {
             *      println("[Message] ${it.server} | ${it.channel} | ${it.user} | ${it.text}")
             *
             *      it.reply("hi!")
             * }
             *
             * listen(ListenerA, ListenerB(), ListenerC(param1, param2))
             */
        }
    }
}