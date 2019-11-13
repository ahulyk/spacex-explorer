package net.hulyka.spacexviewer.json

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@Serializer(forClass = LocalDateTime::class)
object DateSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("DateSerializer")

    override fun serialize(encoder: Encoder, obj: LocalDateTime) {
        encoder.encodeLong(obj.toEpochSecond(ZoneOffset.UTC))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.ofEpochSecond(decoder.decodeLong(), 0, ZoneOffset.UTC)
    }

}