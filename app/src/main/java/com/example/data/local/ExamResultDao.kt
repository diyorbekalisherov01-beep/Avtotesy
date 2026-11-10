package com.example.data.local

import androidx.room.*
import com.example.data.model.ExamResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: ExamResultEntity): Long

    @Query("SELECT * FROM exam_results ORDER BY timestamp DESC")
    fun getAllResultsFlow(): Flow<List<ExamResultEntity>>

    @Query("SELECT * FROM exam_results ORDER BY timestamp DESC")
    suspend fun getAllResults(): List<ExamResultEntity>

    @Query("SELECT * FROM exam_results WHERE id = :id LIMIT 1")
    suspend fun getResultById(id: Long): ExamResultEntity?

    @Query("SELECT COUNT(*) FROM exam_results")
    suspend fun getTotalExamCount(): Int

    @Query("SELECT COUNT(*) FROM exam_results WHERE passed = 1")
    suspend fun getPassedExamCount(): Int

    @Query("SELECT AVG(correctAnswers * 100.0 / totalQuestions) FROM exam_results")
    suspend fun getAverageScorePercent(): Double?
}
