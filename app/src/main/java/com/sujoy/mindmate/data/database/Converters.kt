package com.sujoy.mindmate.data.database

import androidx.room.TypeConverter
import com.sujoy.mindmate.data.models.MoodsEnum

class Converters {
    @TypeConverter
    fun fromMoodsEnum(mood: MoodsEnum): String {
        return mood.name
    }

    @TypeConverter
    fun toMoodsEnum(moodName: String): MoodsEnum {
        return MoodsEnum.valueOf(moodName)
    }
}