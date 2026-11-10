package com.example

import com.example.data.local.QuestionCategories
import com.example.data.local.QuestionSeedData
import com.example.data.local.RoadSignsSeedData
import com.example.data.model.ExamMode
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun verifyOfficial20Categories() {
        assertEquals(20, QuestionCategories.list.size)
        assertEquals("Umumiy qoidalar", QuestionCategories.getCategoryName(1))
        assertEquals("Chorrahada harakatlanish", QuestionCategories.getCategoryName(6))
        assertEquals("Haydovchilarning umumiy majburiyatlari", QuestionCategories.getCategoryName(20))
    }

    @Test
    fun verifySeedQuestionsIntegrity() {
        val questions = QuestionSeedData.initialQuestions
        assertTrue(questions.size >= 24)

        for (q in questions) {
            assertEquals("Har bir savolda 4 ta variant bo'lishi shart", 4, q.optionsUzLatin.size)
            assertEquals("Kirill yozuvida ham 4 ta variant bo'lishi shart", 4, q.optionsUzCyrillic.size)
            assertEquals("Rus tilida ham 4 ta variant bo'lishi shart", 4, q.optionsRu.size)
            assertTrue("To'g'ri javob indeksi 0 dan 3 gacha bo'lishi shart", q.correctOptionIndex in 0..3)
            assertTrue("Tushuntirish bo'sh bo'lmasligi shart", q.explanationUzLatin.isNotEmpty())
            assertTrue("Qonuniy asos ko'rsatilgan bo'lishi shart", q.lawReference.isNotEmpty())
            assertTrue("Toifa 1 dan 20 gacha bo'lishi shart", q.categoryId in 1..20)
        }
    }

    @Test
    fun verifyRoadSignsDirectory() {
        val signs = RoadSignsSeedData.signs
        assertTrue(signs.isNotEmpty())
        assertTrue(signs.any { it.code == "2.1" })
        assertTrue(signs.any { it.code == "3.27" })
        assertTrue(signs.any { it.category == "Ogohlantiruvchi" })
        assertTrue(signs.any { it.category == "Imtiyoz" })
        assertTrue(signs.any { it.category == "Taqiqlovchi" })
    }

    @Test
    fun verifyExamModes() {
        assertEquals(13, ExamMode.values().size)
    }
}
