package dev.kotx.diskord

import dev.kotx.diskord.event.Event
import dev.kotx.diskord.event.EventHandler
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.reflect

class DiskordBuilder(
    private val token: String
) {
    val listeners = mutableMapOf<KClass<out Event>, KFunction<Unit>>()

    @Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST")
    inline fun <reified T : Event> listen(noinline action: suspend (T) -> Unit): DiskordBuilder {
        val func = action.reflect()!!
        listeners[func.parameters[0].type.classifier as KClass<out Event>] = func

        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun listen(vararg listener: Any): DiskordBuilder {
        listener.flatMap {
            it::class.functions
                .filter { it.annotations.any { it is EventHandler } }
                .filter { it.parameters.size == 2 }
                .filter { (it.parameters[1].type.classifier as KClass<*>).isSubclassOf(Event::class) }
                .map { it.parameters[1].type.classifier as KClass<out Event> to it as KFunction<Unit> }
        }.forEach { func ->
            listeners[func.first] = func.second
        }

        return this
    }

    fun build(): Diskord {
        return DiskordImpl(token)
    }
}