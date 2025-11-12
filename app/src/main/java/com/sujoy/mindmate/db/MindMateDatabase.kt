package com.sujoy.mindmate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sujoy.mindmate.models.JournalItemModel

@Database(entities = [JournalItemModel::class], version = 1, exportSchema = false)
abstract class MindMateDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDAO

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
