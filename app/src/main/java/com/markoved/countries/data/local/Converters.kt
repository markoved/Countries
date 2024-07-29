package com.markoved.countries.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(json: String): List<String> {
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(json, type)
    }
}