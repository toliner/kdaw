@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package dev.kotx.kdaw.entity

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable
class ApplicationCommand(
    val id: Long,
    @SerialName("application_id") val applicationId: Long,
    val name: String,
    val description: String,
    val options: List<ApplicationCommandOption>? = null,
    @SerialName("default_permission") val defaultPermission: Boolean? = null
) {
    @Serializable
    class ApplicationCommandOption(
        val type: ApplicationCommandOptionType,
        val name: String,
        val description: String,
        val required: Boolean? = null,
        val choices: List<ApplicationCommandOptionChoice>? = null,
        val options: List<ApplicationCommandOption>? = null
    ) {

        @OptIn(ExperimentalSerializationApi::class)
        @Serializer(forClass = ApplicationCommandOptionType::class)
        object ApplicationCommandOptionTypeSerializer: KSerializer<ApplicationCommandOptionType> {
            override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("type", PrimitiveKind.INT)

            override fun serialize(encoder: Encoder, value: ApplicationCommandOptionType) {
                encoder.encodeInt(value.value)
            }

            override fun deserialize(decoder: Decoder): ApplicationCommandOptionType {
                return ApplicationCommandOptionType.values().find { it.value == decoder.decodeInt() }!!
            }
        }

        @Serializable(with = ApplicationCommandOptionTypeSerializer::class)
        enum class ApplicationCommandOptionType(val value: Int) {
            SUB_COMMAND(1),
            SUB_COMMAND_GROUP(2),
            STRING(3),
            INTEGER(4),
            BOOLEAN(5),
            USER(6),
            CHANNEL(7),
            ROLE(8),
            MENTIONABLE(9),
        }

        @Serializable
        class ApplicationCommandOptionChoice(
            val name: String,
            val value: String
        )
    }
}