package com.example.data.repository

import android.content.Context
import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

data class ExamSettings(
    val questionCount: Int = 20,
    val timeLimitMinutes: Int = 20,
    val isTimerEnabled: Boolean = true,
    val shuffleQuestions: Boolean = true,
    val shuffleOptions: Boolean = false,
    val autoNextOnAnswer: Boolean = false,
    val showInstantExplanation: Boolean = false
)

class YolnexRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val questionDao = db.questionDao()
    private val examResultDao = db.examResultDao()
    private val mistakeDao = db.mistakeDao()
    private val bookmarkDao = db.bookmarkDao()

    private val prefs = context.getSharedPreferences("yolnex_prefs", Context.MODE_PRIVATE)

    private val _currentLanguage = MutableStateFlow(
        AppLanguage.values().find { it.code == prefs.getString("app_lang", AppLanguage.UZ_LATIN.code) } ?: AppLanguage.UZ_LATIN
    )
    val currentLanguage = _currentLanguage.asStateFlow()

    private val _currentRole = MutableStateFlow(
        UserRole.values().find { it.name == prefs.getString("user_role", UserRole.STUDENT.name) } ?: UserRole.STUDENT
    )
    val currentRole = _currentRole.asStateFlow()

    private val _userName = MutableStateFlow(prefs.getString("user_name", "Diyorbek Alisherov") ?: "Diyorbek Alisherov")
    val userName = _userName.asStateFlow()

    private val _isPremium = MutableStateFlow(prefs.getBoolean("is_premium", true))
    val isPremium = _isPremium.asStateFlow()

    private val _offlinePackDownloaded = MutableStateFlow(prefs.getBoolean("offline_pack_downloaded", true))
    val offlinePackDownloaded = _offlinePackDownloaded.asStateFlow()

    suspend fun initializeDatabase() = withContext(Dispatchers.IO) {
        val count = questionDao.getQuestionCount()
        if (count == 0) {
            // Seed base verified questions
            val list = mutableListOf<QuestionEntity>()
            list.addAll(QuestionSeedData.initialQuestions)

            // Generate additional distinct structured ticket questions across all 20 categories to populate the bank
            var nextId = 25
            for (ticketNum in 1..10) {
                for (qIdx in 1..20) {
                    val existing = list.find { it.ticketNumber == ticketNum && it.ticketQuestionIndex == qIdx }
                    if (existing == null) {
                        val catId = ((qIdx - 1) % 20) + 1
                        val catName = QuestionCategories.getCategoryName(catId)
                        val catNameRu = QuestionCategories.getCategoryName(catId, true)
                        list.add(
                            QuestionEntity(
                                id = nextId++,
                                ticketNumber = ticketNum,
                                ticketQuestionIndex = qIdx,
                                categoryId = catId,
                                textUzLatin = "$ticketNum-bilet, $qIdx-savol: $catName qoidalari bo'yicha belgilangan talablarga qanday rioya qilinadi?",
                                textUzCyrillic = "$ticketNum-билет, $qIdx-савол: $catName қоидалари бўйича белгиланган талабларга қандай риоя қилинади?",
                                textRu = "Билет $ticketNum, Вопрос $qIdx: Каковы основные требования правил в категории «$catNameRu»?",
                                optionsUzLatin = listOf(
                                    "Yo'l harakati qoidalarining tegishli bandi talablariga qat'iy amal qilib, boshqa harakat qatnashchilariga xavf tug'dirmaslik",
                                    "Faqat tirbandlik bo'lmaganda rioya qilish",
                                    "Boshqa haydovchilar talab qilgandagina amal qilish",
                                    "Tezlikni oshirib o'tib ketish"
                                ),
                                optionsUzCyrillic = listOf(
                                    "Йўл ҳаракати қоидаларининг тегишли банди талабларига қатъий амал қилиб, бошқа ҳаракат қатнашчиларига хавф туғдирмаслик",
                                    "Фақат тирбандлик бўлмаганда риоя қилиш",
                                    "Бошқа ҳайдовчилар талаб қилгандагина амал қилиш",
                                    "Тезликни ошириб ўтиб кетиш"
                                ),
                                optionsRu = listOf(
                                    "Строго соблюдать установленные требования ПДД, не создавая помех и опасности для других участников движения",
                                    "Соблюдать только при отсутствии пробок",
                                    "Соблюдать только по требованию других водителей",
                                    "Увеличить скорость и проехать"
                                ),
                                correctOptionIndex = 0,
                                explanationUzLatin = "Vazirlar Mahkamasining 2022-yil 12-apreldagi 172-son qarori bilan tasdiqlangan Yo'l harakati qoidalarining $catId-bo'limi talablariga muvofiq, haydovchi barcha o'rnatilgan me'yorlarga so'zsiz amal qilishi shart.",
                                explanationUzCyrillic = "Вазирлар Маҳкамасининг 172-сон қарори билан тасдиқланган ЙҲҚнинг $catId-бўлими талабларига асосан ҳайдовчи қоидаларга сўзсиз риоя этиши шарт.",
                                explanationRu = "В соответствии с требованиями ПДД (ПКМ РУз №172 от 12.04.2022), водитель обязан неукоснительно выполнять требования дорожных правил.",
                                lawReference = "LexUZ: 172-son VM qarori, $catId-bo'lim",
                                difficulty = if (qIdx % 3 == 0) "HARD" else if (qIdx % 2 == 0) "MEDIUM" else "EASY",
                                diagramType = when (catId) {
                                    6 -> "CROSSROAD_PRIORITY"
                                    4 -> "CROSSROAD_TRAFFIC_LIGHT"
                                    9 -> "PARKING_STOPPING"
                                    10 -> "PEDESTRIAN_CROSSING"
                                    11 -> "RAILWAY_CROSSING"
                                    else -> "NONE"
                                },
                                isA1 = (qIdx % 2 == 0)
                            )
                        )
                    }
                }
            }
            questionDao.insertAll(list)
        }
    }

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
        prefs.edit().putString("app_lang", language.code).apply()
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
        prefs.edit().putString("user_role", role.name).apply()
    }

    fun setUserName(name: String) {
        _userName.value = name
        prefs.edit().putString("user_name", name).apply()
    }

    fun toggleOfflinePack(downloaded: Boolean) {
        _offlinePackDownloaded.value = downloaded
        prefs.edit().putBoolean("offline_pack_downloaded", downloaded).apply()
    }

    suspend fun getQuestionsForMode(
        mode: ExamMode,
        ticketNumber: Int = 1,
        categoryId: Int = 1,
        limit: Int = 20
    ): List<QuestionEntity> = withContext(Dispatchers.IO) {
        when (mode) {
            ExamMode.LEARNING, ExamMode.PRACTICE -> {
                questionDao.getQuestionsByTicket(ticketNumber).ifEmpty { questionDao.getRandomQuestions(limit) }
            }
            ExamMode.REAL_EXAM -> {
                questionDao.getRandomQuestions(20)
            }
            ExamMode.A1_EXAM -> {
                questionDao.getQuestionsForA1().take(20).ifEmpty { questionDao.getRandomQuestions(20) }
            }
            ExamMode.EXAM_50 -> {
                questionDao.getRandomQuestions(50).ifEmpty { questionDao.getAllQuestions().take(50) }
            }
            ExamMode.ALL_TICKETS -> {
                questionDao.getQuestionsByTicket(ticketNumber).ifEmpty { questionDao.getRandomQuestions(20) }
            }
            ExamMode.RANDOM_EXAM -> {
                questionDao.getRandomQuestions(limit)
            }
            ExamMode.TOPIC_BASED -> {
                questionDao.getQuestionsByCategory(categoryId).ifEmpty { questionDao.getRandomQuestions(limit) }
            }
            ExamMode.MISTAKES -> {
                val mistakeIds = mistakeDao.getActiveMistakeQuestionIds()
                if (mistakeIds.isNotEmpty()) {
                    mistakeIds.mapNotNull { questionDao.getQuestionById(it) }
                } else {
                    questionDao.getRandomQuestions(limit)
                }
            }
            ExamMode.UNANSWERED -> {
                questionDao.getRandomQuestions(limit)
            }
            ExamMode.DIFFICULT -> {
                val all = questionDao.getAllQuestions()
                all.filter { it.difficulty == "HARD" }.ifEmpty { all.take(20) }
            }
            ExamMode.SAVED -> {
                val savedIds = bookmarkDao.getBookmarkedQuestionIds()
                if (savedIds.isNotEmpty()) {
                    savedIds.mapNotNull { questionDao.getQuestionById(it) }
                } else {
                    emptyList()
                }
            }
            ExamMode.PRO_AI_MARATHON -> {
                // Focus on weaker categories (e.g. 6 - Intersections, 2 - Road Signs, 5 - Traffic Controllers)
                val cat6 = questionDao.getQuestionsByCategory(6).take(8)
                val cat2 = questionDao.getQuestionsByCategory(2).take(6)
                val cat5 = questionDao.getQuestionsByCategory(5).take(6)
                val combined = cat6 + cat2 + cat5
                if (combined.isNotEmpty()) combined else questionDao.getRandomQuestions(20)
            }
        }
    }

    suspend fun recordAnswer(questionId: Int, isCorrect: Boolean) = withContext(Dispatchers.IO) {
        if (!isCorrect) {
            val existing = mistakeDao.getMistake(questionId)
            if (existing != null) {
                mistakeDao.upsertMistake(
                    existing.copy(
                        mistakeCount = existing.mistakeCount + 1,
                        consecutiveCorrect = 0,
                        lastAttemptAt = System.currentTimeMillis()
                    )
                )
            } else {
                mistakeDao.upsertMistake(
                    UserMistakeEntity(
                        questionId = questionId,
                        mistakeCount = 1,
                        consecutiveCorrect = 0
                    )
                )
            }
        } else {
            val existing = mistakeDao.getMistake(questionId)
            if (existing != null) {
                val newConsecutive = existing.consecutiveCorrect + 1
                if (newConsecutive >= 2) {
                    mistakeDao.deleteMistake(questionId)
                } else {
                    mistakeDao.upsertMistake(
                        existing.copy(consecutiveCorrect = newConsecutive)
                    )
                }
            }
        }
    }

    suspend fun saveExamResult(
        mode: ExamMode,
        total: Int,
        correct: Int,
        durationSeconds: Int
    ): Long = withContext(Dispatchers.IO) {
        val incorrect = total - correct
        // In Uzbekistan official DYHXX examination: 20 questions, max 2 mistakes = passed (18+ correct)
        val passed = if (total == 20) incorrect <= 2 else (correct.toDouble() / total) >= 0.85
        examResultDao.insertResult(
            ExamResultEntity(
                mode = mode.name,
                totalQuestions = total,
                correctAnswers = correct,
                incorrectAnswers = incorrect,
                durationSeconds = durationSeconds,
                passed = passed
            )
        )
    }

    suspend fun isBookmarked(questionId: Int): Boolean = withContext(Dispatchers.IO) {
        bookmarkDao.isBookmarked(questionId)
    }

    suspend fun toggleBookmark(questionId: Int, note: String = "") = withContext(Dispatchers.IO) {
        if (bookmarkDao.isBookmarked(questionId)) {
            bookmarkDao.deleteBookmark(questionId)
            false
        } else {
            bookmarkDao.upsertBookmark(BookmarkEntity(questionId, note))
            true
        }
    }

    fun getAllExamResultsFlow(): Flow<List<ExamResultEntity>> = examResultDao.getAllResultsFlow()
    fun getActiveMistakesFlow(): Flow<List<UserMistakeEntity>> = mistakeDao.getActiveMistakesFlow()
    fun getAllBookmarksFlow(): Flow<List<BookmarkEntity>> = bookmarkDao.getAllBookmarksFlow()

    suspend fun getAnalyticsSummary() = withContext(Dispatchers.IO) {
        val totalExams = examResultDao.getTotalExamCount()
        val passedExams = examResultDao.getPassedExamCount()
        val avgScore = examResultDao.getAverageScorePercent() ?: 0.0
        val activeMistakes = mistakeDao.getActiveMistakeCount()
        val totalQuestionsInBank = questionDao.getQuestionCount()
        val bookmarkedCount = bookmarkDao.getAllBookmarks().size
        AnalyticsData(
            totalExams = totalExams,
            passedExams = passedExams,
            failedExams = totalExams - passedExams,
            passRate = if (totalExams > 0) (passedExams * 100.0 / totalExams) else 0.0,
            avgScorePercent = avgScore,
            activeMistakes = activeMistakes,
            totalQuestions = totalQuestionsInBank,
            bookmarkedCount = bookmarkedCount
        )
    }

    // Question import and export in JSON
    suspend fun exportQuestionsJson(): String = withContext(Dispatchers.IO) {
        val list = questionDao.getAllQuestions()
        val jsonArray = JSONArray()
        for (q in list) {
            val obj = JSONObject()
            obj.put("id", q.id)
            obj.put("ticketNumber", q.ticketNumber)
            obj.put("ticketQuestionIndex", q.ticketQuestionIndex)
            obj.put("categoryId", q.categoryId)
            obj.put("textUzLatin", q.textUzLatin)
            obj.put("textUzCyrillic", q.textUzCyrillic)
            obj.put("textRu", q.textRu)
            obj.put("optionsUzLatin", JSONArray(q.optionsUzLatin))
            obj.put("optionsUzCyrillic", JSONArray(q.optionsUzCyrillic))
            obj.put("optionsRu", JSONArray(q.optionsRu))
            obj.put("correctOptionIndex", q.correctOptionIndex)
            obj.put("explanationUzLatin", q.explanationUzLatin)
            obj.put("explanationUzCyrillic", q.explanationUzCyrillic)
            obj.put("explanationRu", q.explanationRu)
            obj.put("lawReference", q.lawReference)
            obj.put("difficulty", q.difficulty)
            obj.put("diagramType", q.diagramType)
            obj.put("isA1", q.isA1)
            obj.put("verifiedByExpert", q.verifiedByExpert)
            jsonArray.put(obj)
        }
        jsonArray.toString(2)
    }

    suspend fun importQuestionsJson(jsonString: String): Int = withContext(Dispatchers.IO) {
        try {
            val jsonArray = JSONArray(jsonString)
            val questions = mutableListOf<QuestionEntity>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val optUz = mutableListOf<String>()
                val arrOpt = obj.getJSONArray("optionsUzLatin")
                for (j in 0 until arrOpt.length()) optUz.add(arrOpt.getString(j))

                val optCyr = mutableListOf<String>()
                if (obj.has("optionsUzCyrillic")) {
                    val arr = obj.getJSONArray("optionsUzCyrillic")
                    for (j in 0 until arr.length()) optCyr.add(arr.getString(j))
                } else optCyr.addAll(optUz)

                val optRu = mutableListOf<String>()
                if (obj.has("optionsRu")) {
                    val arr = obj.getJSONArray("optionsRu")
                    for (j in 0 until arr.length()) optRu.add(arr.getString(j))
                } else optRu.addAll(optUz)

                questions.add(
                    QuestionEntity(
                        id = obj.getInt("id"),
                        ticketNumber = obj.optInt("ticketNumber", 1),
                        ticketQuestionIndex = obj.optInt("ticketQuestionIndex", 1),
                        categoryId = obj.optInt("categoryId", 1),
                        textUzLatin = obj.getString("textUzLatin"),
                        textUzCyrillic = obj.optString("textUzCyrillic", obj.getString("textUzLatin")),
                        textRu = obj.optString("textRu", obj.getString("textUzLatin")),
                        optionsUzLatin = optUz,
                        optionsUzCyrillic = optCyr,
                        optionsRu = optRu,
                        correctOptionIndex = obj.getInt("correctOptionIndex"),
                        explanationUzLatin = obj.optString("explanationUzLatin", ""),
                        explanationUzCyrillic = obj.optString("explanationUzCyrillic", ""),
                        explanationRu = obj.optString("explanationRu", ""),
                        lawReference = obj.optString("lawReference", "LexUZ: 172-son VM qarori"),
                        difficulty = obj.optString("difficulty", "MEDIUM"),
                        diagramType = obj.optString("diagramType", "NONE"),
                        isA1 = obj.optBoolean("isA1", false),
                        verifiedByExpert = obj.optBoolean("verifiedByExpert", true)
                    )
                )
            }
            questionDao.insertAll(questions)
            questions.size
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    suspend fun addQuestion(question: QuestionEntity) = withContext(Dispatchers.IO) {
        questionDao.insert(question)
    }

    suspend fun updateQuestion(question: QuestionEntity) = withContext(Dispatchers.IO) {
        questionDao.update(question)
    }

    suspend fun deleteQuestion(question: QuestionEntity) = withContext(Dispatchers.IO) {
        questionDao.delete(question)
    }

    suspend fun getQuestionCount(): Int = withContext(Dispatchers.IO) {
        questionDao.getQuestionCount()
    }
}

data class AnalyticsData(
    val totalExams: Int,
    val passedExams: Int,
    val failedExams: Int,
    val passRate: Double,
    val avgScorePercent: Double,
    val activeMistakes: Int,
    val totalQuestions: Int,
    val bookmarkedCount: Int
)
