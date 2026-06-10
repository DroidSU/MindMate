package com.sujoy.mindmate.v1.data.database

import androidx.room.TypeConverter
import com.sujoy.mindmate.v1.data.models.MoodsEnum

class Converters {
    @TypeConverter
    fun fromMoodsEnum(mood: MoodsEnum): String {
        return mood.name
    }

    @TypeConverter
    fun toMoodsEnum(moodName: String): MoodsEnum {
        return MoodsEnum.valueOf(moodName)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}