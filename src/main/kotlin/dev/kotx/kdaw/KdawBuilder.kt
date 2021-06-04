package dev.kotx.kdaw

import dev.kotx.kdaw.event.*
import dev.kotx.kdaw.gateway.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

class KdawBuilder(
    private val token: String
) {
    val listeners = mutableMapOf<KClass<out Event>, KFunction<Unit>>()
    private var intents = 0

    @Suppress("EXPERIMENTAL_API_USAGE", "UNCHECKED_CAST")
    inline fun <reified T : Event> listen(noinline action: suspend (T) -> Unit): KdawBuilder {
        val func = action.reflect()!!
        listeners[func.parameters[0].type.classifier as KClass<out Event>] = func

        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun listen(vararg listener: Any): KdawBuilder {
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

    fun enableIntents(vararg intent: GatewayIntent): KdawBuilder {
        intents = intent.sumOf { it.decimal }

        return this
    }

    fun build(): Kdaw {
        return KdawImpl(token, listeners, intents)
    }
}