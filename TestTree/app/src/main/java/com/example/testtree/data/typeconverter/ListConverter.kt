package com.example.testtree.data.typeconverter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.testtree.domain.models.ListNodes
import com.example.testtree.domain.models.Node
import com.google.gson.reflect.TypeToken
import java.util.*

@ProvidedTypeConverter
class ListConverter(private val jsonParser: JsonParser) {

    @TypeConverter
    fun toMeaningJson(meaning: LinkedList<String>?) : String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<LinkedList<String>?>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): LinkedList<String> {
        return (jsonParser.fromJson<LinkedList<String>?>(
            json,
            object: TypeToken<LinkedList<String>?>(){}.type
        ) ?: LinkedList<String>())
    }
}