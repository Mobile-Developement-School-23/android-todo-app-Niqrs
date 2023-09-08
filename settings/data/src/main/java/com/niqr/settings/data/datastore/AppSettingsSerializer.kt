package com.niqr.settings.data.datastore

import androidx.datastore.core.Serializer
import com.niqr.settings.data.model.AppSettingsDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class AppSettingsSerializer: Serializer<AppSettingsDto> {
    override val defaultValue: AppSettingsDto
        get() = AppSettingsDto()

    override suspend fun readFrom(input: InputStream): AppSettingsDto {
        return try {
            Json.decodeFromString(
                deserializer = AppSettingsDto.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.run { printStackTrace() }
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettingsDto, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppSettingsDto.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}