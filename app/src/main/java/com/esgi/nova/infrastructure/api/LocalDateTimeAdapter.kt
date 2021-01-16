package com.esgi.nova.infrastructure.api

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime?>, JsonSerializer<LocalDateTime?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {

        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(json.asString, formatter)
    }

    override fun serialize(
        time: LocalDateTime?,
        typeOfT: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        return JsonPrimitive(time?.format(formatter))
    }
}