package com.project.jufood.data

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromString(value: String): List<Ingredients> {
        val listType = object : TypeToken<List<Ingredients>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Ingredients>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}