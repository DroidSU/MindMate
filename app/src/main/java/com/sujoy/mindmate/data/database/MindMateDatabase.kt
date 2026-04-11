package com.sujoy.mindmate.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sujoy.mindmate.data.models.AverageMoodDBModel
import com.sujoy.mindmate.data.models.JournalAnalyzedDbModel
import com.sujoy.mindmate.data.models.JournalItemDBModel
import com.sujoy.mindmate.utils.ConstantsManager

@Database(
    entities = [JournalItemDBModel::class, JournalAnalyzedDbModel::class, AverageMoodDBModel::class],
    version = 4,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class MindMateDatabase : RoomDatabase() {
    abstract fun appDao(): AppDAO

    companion object {
        @Volatile
        private var INSTANCE: MindMateDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    ALTER TABLE ${ConstantsManager.TABLE_JOURNAL_ITEMS} ADD COLUMN ${ConstantsManager.MOOD_SCORE} FLOAT
                """.trimIndent()
                )
            }

        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `average_mood` (`date` TEXT NOT NULL, `avg_mood_score` REAL NOT NULL, PRIMARY KEY(`date`))
                """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): MindMateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MindMateDatabase::class.java,
                    "mind_mate_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
