package dev.kotx.diskord

import dev.kotx.diskord.event.Event

class DiskordTest {
    fun launchTest() {
        Diskord.create("<< insert bot token here >>") {
            listen<Event> {

            }
        }
    }
}