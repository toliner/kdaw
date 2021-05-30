package dev.kotx.diskord

import dev.kotx.diskord.event.Event
import dev.kotx.diskord.event.EventHandler
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.reflect

abstract class Diskord(
    val token: String
) {
    companion object {
        fun create(token: String, block: DiskordBuilder.() -> Unit): Diskord = DiskordBuilder(token).apply(block).build()
    }
}