package com.example.data.local

import androidx.room.*
import com.example.data.model.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity)

    @Update
    suspend fun update(question: QuestionEntity)

    @Delete
    suspend fun delete(question: QuestionEntity)

    @Query("SELECT * FROM questions ORDER BY ticketNumber ASC, ticketQuestionIndex ASC")
    fun getAllQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions ORDER BY ticketNumber ASC, ticketQuestionIndex ASC")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int

    @Query("SELECT * FROM questions WHERE id = :id LIMIT 1")
    suspend fun getQuestionById(id: Int): QuestionEntity?

    @Query("SELECT * FROM questions WHERE ticketNumber = :ticketNumber ORDER BY ticketQuestionIndex ASC")
    suspend fun getQuestionsByTicket(ticketNumber: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId ORDER BY id ASC")
    suspend fun getQuestionsByCategory(categoryId: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE isA1 = 1 ORDER BY id ASC")
    suspend fun getQuestionsForA1(): List<QuestionEntity>

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomQuestions(limit: Int): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE textUzLatin LIKE '%' || :query || '%' OR textUzCyrillic LIKE '%' || :query || '%' OR textRu LIKE '%' || :query || '%'")
    suspend fun searchQuestions(query: String): List<QuestionEntity>

    @Query("SELECT DISTINCT ticketNumber FROM questions ORDER BY ticketNumber ASC")
    suspend fun getAllTicketNumbers(): List<Int>
}
