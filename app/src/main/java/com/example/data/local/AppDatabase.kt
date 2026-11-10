package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.BookmarkEntity
import com.example.data.model.ExamResultEntity
import com.example.data.model.QuestionEntity
import com.example.data.model.UserMistakeEntity

@Database(
    entities = [
        QuestionEntity::class,
        ExamResultEntity::class,
        UserMistakeEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun examResultDao(): ExamResultDao
    abstract fun mistakeDao(): MistakeDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yolnex_exam_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
