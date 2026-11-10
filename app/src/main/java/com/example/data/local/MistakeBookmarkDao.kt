package com.example.data.local

import androidx.room.*
import com.example.data.model.BookmarkEntity
import com.example.data.model.UserMistakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MistakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMistake(mistake: UserMistakeEntity)

    @Query("SELECT * FROM user_mistakes WHERE consecutiveCorrect < 2 ORDER BY mistakeCount DESC, lastAttemptAt DESC")
    suspend fun getActiveMistakes(): List<UserMistakeEntity>

    @Query("SELECT * FROM user_mistakes WHERE consecutiveCorrect < 2 ORDER BY mistakeCount DESC")
    fun getActiveMistakesFlow(): Flow<List<UserMistakeEntity>>

    @Query("SELECT questionId FROM user_mistakes WHERE consecutiveCorrect < 2")
    suspend fun getActiveMistakeQuestionIds(): List<Int>

    @Query("SELECT * FROM user_mistakes WHERE questionId = :questionId LIMIT 1")
    suspend fun getMistake(questionId: Int): UserMistakeEntity?

    @Query("SELECT COUNT(*) FROM user_mistakes WHERE consecutiveCorrect < 2")
    suspend fun getActiveMistakeCount(): Int

    @Query("DELETE FROM user_mistakes WHERE questionId = :questionId")
    suspend fun deleteMistake(questionId: Int)

    @Query("DELETE FROM user_mistakes")
    suspend fun clearAllMistakes()
}

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    suspend fun getAllBookmarks(): List<BookmarkEntity>

    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarksFlow(): Flow<List<BookmarkEntity>>

    @Query("SELECT questionId FROM bookmarks")
    suspend fun getBookmarkedQuestionIds(): List<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE questionId = :questionId)")
    suspend fun isBookmarked(questionId: Int): Boolean

    @Query("DELETE FROM bookmarks WHERE questionId = :questionId")
    suspend fun deleteBookmark(questionId: Int)
}
