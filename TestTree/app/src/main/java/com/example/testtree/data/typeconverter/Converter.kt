package com.example.testtree.data.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.testtree.domain.models.Node
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter(private val jsonParser: JsonParser) {

    @TypeConverter
    fun toMeaningJson(meaning: Node?) : String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<Node?>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): Node {
        return (jsonParser.fromJson<Node?>(
            json,
            object: TypeToken<Node?>(){}.type
        ) ?: Node())
    }


}