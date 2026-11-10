package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class AppLanguage(val code: String, val label: String) {
    UZ_LATIN("uz_lat", "O'zbekcha"),
    UZ_CYRILLIC("uz_cyr", "Ўзбекча"),
    RUSSIAN("ru", "Русский")
}

enum class UserRole(val title: String) {
    STUDENT("O'quvchi"),
    TEACHER("O'qituvchi / Instruktor"),
    DRIVING_SCHOOL("Avtomaktab rahbari"),
    ADMIN("Administrator")
}

enum class ExamMode(val title: String, val description: String) {
    LEARNING("O'rganish tartibi", "Javob va tushuntirish darhol ko'rsatiladi, vaqt chegarasiz"),
    PRACTICE("Mashg'ulot", "Javob belgilangandan so'ng xato va to'g'rilik ko'rsatiladi"),
    REAL_EXAM("Haqiqiy Imtihon (DYHXX)", "20 ta savol, 20 daqiqa, maksimal 2 ta xatoga ruxsat"),
    A1_EXAM("A1 / A Toifa Imtihoni", "Mototransport vositalari bo'yicha maxsus sinov"),
    EXAM_50("50 talik Marafon", "Katta sinov: 50 ta savol, 50 daqiqa"),
    ALL_TICKETS("Barcha Biletlar", "1-dan 60-gacha bo'lgan rasmiy biletlar to'plami"),
    RANDOM_EXAM("Tasodifiy Imtihon", "Baza bo'yicha aralash savollar"),
    TOPIC_BASED("Mavzulashtirilgan", "20 ta rasmiy mavzulardan biri bo'yicha sinov"),
    MISTAKES("Xatolar ustida ishlash", "Oldin yo'l qo'yilgan xato savollarni qayta ishlash"),
    UNANSWERED("Yechilmagan savollar", "Hali biror marta ishlanmagan savollar"),
    DIFFICULT("Qiyin savollar", "Foydalanuvchilar eng ko'p xato qiladigan savollar"),
    SAVED("Tanlangan savollar", "Shaxsiy saqlangan va qayd qilingan savollar"),
    PRO_AI_MARATHON("PRO AI Marafon", "Sun'iy intellekt tomonidan tahlil qilingan zaif mavzular")
}

enum class QuestionDifficulty {
    EASY, MEDIUM, HARD
}

enum class DiagramType {
    NONE,
    CROSSROAD_EQUAL,             // Teng huquqli chorraha
    CROSSROAD_PRIORITY,          // Asosiy va ikkinchi darajali yo'l
    CROSSROAD_TRAFFIC_LIGHT,     // Svetoforli chorraha
    ROUNDABOUT,                  // Aylanma harakat chorrahasi
    PEDESTRIAN_CROSSING,         // Piyodalar o'tish joyi
    OVERTAKING_MANEUVER,         // Quvib o'tish holati
    PARKING_STOPPING,            // To'xtash va to'xtab turish
    POLICE_REGULATOR,            // Tartibga soluvchi ishoralari
    RAILWAY_CROSSING             // Temir yo'l kesishmasi
}

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: Int,
    val ticketNumber: Int,
    val ticketQuestionIndex: Int,
    val categoryId: Int, // 1 to 20
    val textUzLatin: String,
    val textUzCyrillic: String,
    val textRu: String,
    val optionsUzLatin: List<String>,
    val optionsUzCyrillic: List<String>,
    val optionsRu: List<String>,
    val correctOptionIndex: Int, // 0..3
    val explanationUzLatin: String,
    val explanationUzCyrillic: String,
    val explanationRu: String,
    val lawReference: String, // e.g. "LexUZ: 172-son VM qarori, 34-band"
    val difficulty: String = "MEDIUM",
    val diagramType: String = "NONE",
    val hasImage: Boolean = false,
    val isA1: Boolean = false,
    val verifiedByExpert: Boolean = true,
    val authorStatus: String = "Tasdiqlangan",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "exam_results")
data class ExamResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mode: String,
    val timestamp: Long = System.currentTimeMillis(),
    val totalQuestions: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val durationSeconds: Int,
    val passed: Boolean,
    val categoryStatsJson: String = "{}"
)

@Entity(tableName = "user_mistakes")
data class UserMistakeEntity(
    @PrimaryKey val questionId: Int,
    val mistakeCount: Int = 1,
    val consecutiveCorrect: Int = 0,
    val lastAttemptAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val questionId: Int,
    val userNote: String = "",
    val savedAt: Long = System.currentTimeMillis()
)

data class QuestionCategory(
    val id: Int,
    val nameUz: String,
    val nameRu: String,
    val iconName: String,
    val totalQuestions: Int = 60
)

data class RoadSignItem(
    val id: String,
    val code: String,
    val category: String, // "Ogohlantiruvchi", "Imtiyoz", "Taqiqlovchi", "Buyuruvchi", "Axborot-ishora", "Servis", "Qo'shimcha"
    val nameUz: String,
    val nameRu: String,
    val descriptionUz: String,
    val descriptionRu: String,
    val shape: String, // "TRIANGLE_RED", "OCTAGON_RED", "CIRCLE_RED", "CIRCLE_BLUE", "SQUARE_BLUE", "DIAMOND_YELLOW"
    val symbolType: String
)

data class VideoLessonItem(
    val id: String,
    val titleUz: String,
    val duration: String,
    val category: String,
    val isFree: Boolean,
    val instructor: String,
    val descriptionUz: String,
    val views: Int
)

data class TeacherAssignmentItem(
    val id: String,
    val title: String,
    val groupName: String,
    val categoryName: String,
    val deadline: String,
    val assignedCount: Int,
    val completedCount: Int,
    val avgScore: Double
)

data class DrivingSchoolItem(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
    val phone: String,
    val rating: Double,
    val reviewCount: Int,
    val passRatePercent: Int,
    val priceRange: String
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val city: String,
    val score: Int,
    val winRatio: String
)
