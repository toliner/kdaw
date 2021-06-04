package dev.kotx.diskord

import dev.kotx.diskord.event.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

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