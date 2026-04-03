package com.sujoy.mindmate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel

@Database(
    entities = [JournalItemDBModel::class, JournalAnalyzedDbModel::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class MindMateDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO

    companion object {
        @Volatile
        private var INSTANCE: MindMateDatabase? = null

        fun getDatabase(context: Context): MindMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MindMateDatabase::class.java,
                    "mind_mate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
