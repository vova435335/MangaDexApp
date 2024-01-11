package ru.vld43.mangadexapp.data.network.deserialize

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.vld43.mangadexapp.data.network.response.manga.LocalizedString
import java.lang.reflect.Type

class LocalizedStringDeserializer : JsonDeserializer<LocalizedString> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalizedString? {

        if (json?.isJsonArray == true) {
            return null
        }

        val jsonObject = json?.asJsonObject
        val en = jsonObject?.get("en")?.asString
        val ru = jsonObject?.get("ru")?.asString
        val ja = jsonObject?.get("ja")?.asString
        val uk = jsonObject?.get("uk")?.asString
        val zhHk = jsonObject?.get("zhHk")?.asString
        val zhRo = jsonObject?.get("zhRo")?.asString

        return LocalizedString(en, ru, ja, uk, zhHk, zhRo)
    }
}
