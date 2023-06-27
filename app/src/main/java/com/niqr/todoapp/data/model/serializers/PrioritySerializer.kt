package com.niqr.todoapp.data.model.serializers

import com.niqr.todoapp.data.model.Priority
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object PrioritySerializer: KSerializer<Priority> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Priority", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Priority {
        return when(decoder.decodeString()) {
            "important" -> Priority.HIGH
            "low" -> Priority.LOW
            else -> Priority.COMMON
        }
    }

    override fun serialize(encoder: Encoder, value: Priority) {
        val priority = when(value) {
            Priority.HIGH -> "important"
            Priority.LOW -> "low"
            Priority.COMMON -> "basic"
        }
        encoder.encodeString(priority)
    }
}