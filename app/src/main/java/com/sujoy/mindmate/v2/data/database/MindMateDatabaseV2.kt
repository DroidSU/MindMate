package com.sujoy.mindmate.v2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sujoy.mindmate.v2.data.models.MoodLog

@Database(
    entities = [MoodLog::class],
    version = 1,
    exportSchema = false
)
abstract class MindMateDatabaseV2 : RoomDatabase() {
    abstract fun appDao(): V2DatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: MindMateDatabaseV2? = null

        fun getDatabase(context: Context): MindMateDatabaseV2 {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MindMateDatabaseV2::class.java,
                    "mind_mate_database_v2"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}