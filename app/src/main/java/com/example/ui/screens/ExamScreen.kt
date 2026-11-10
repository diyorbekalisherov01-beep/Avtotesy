package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.QuestionCategories
import com.example.data.model.AppLanguage
import com.example.data.model.ExamMode
import com.example.data.model.QuestionEntity
import com.example.data.repository.YolnexRepository
import com.example.ui.components.*
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCrimson
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    mode: ExamMode,
    ticketNumber: Int,
    categoryId: Int,
    repository: YolnexRepository,
    onFinishExam: (Int, Int, Int, List<Pair<QuestionEntity, Int>>) -> Unit, // total, correct, durationSec, answers
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val currentLang by repository.currentLanguage.collectAsState()

    var questions by remember { mutableStateOf<List<QuestionEntity>>(emptyList()) }
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswers by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) } // index to selected option (0..3)
    var isBookmarked by remember { mutableStateOf(false) }

    // Timer
    val totalTimeSeconds = when (mode) {
        ExamMode.EXAM_50 -> 50 * 60
        ExamMode.LEARNING -> 0 // no timer
        else -> 20 * 60
    }
    var remainingSeconds by remember { mutableStateOf(totalTimeSeconds) }
    var elapsedSeconds by remember { mutableStateOf(0) }

    var showFinishConfirmDialog by remember { mutableStateOf(false) }
    var showQuestionsSheet by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var snackbarHostState = remember { SnackbarHostState() }

    // Load questions
    LaunchedEffect(mode, ticketNumber, categoryId) {
        val loaded = repository.getQuestionsForMode(mode, ticketNumber, categoryId)
        questions = loaded
        if (loaded.isNotEmpty()) {
            isBookmarked = repository.isBookmarked(loaded[0].id)
        }
    }

    // Countdown timer for timed modes
    LaunchedEffect(mode) {
        if (mode != ExamMode.LEARNING) {
            while (remainingSeconds > 0) {
                delay(1000)
                remainingSeconds -= 1
                elapsedSeconds += 1
            }
            if (remainingSeconds == 0 && questions.isNotEmpty()) {
                // Time's up - auto submit
                val correctCount = questions.indices.count { idx ->
                    selectedAnswers[idx] == questions[idx].correctOptionIndex
                }
                val answersList = questions.indices.map { idx ->
                    questions[idx] to (selectedAnswers[idx] ?: -1)
                }
                onFinishExam(questions.size, correctCount, elapsedSeconds, answersList)
            }
        }
    }

    // Check bookmark whenever current index changes
    LaunchedEffect(currentIndex, questions) {
        if (questions.isNotEmpty() && currentIndex < questions.size) {
            isBookmarked = repository.isBookmarked(questions[currentIndex].id)
        }
    }

    BackHandler {
        showFinishConfirmDialog = true
    }

    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = BrandCobalt)
        }
        return
    }

    val currentQ = questions[currentIndex]
    val selectedOption = selectedAnswers[currentIndex]
    val isAnswered = selectedOption != null

    // Determine whether to reveal answer:
    // In LEARNING mode: always revealed after clicking or from start
    // In PRACTICE mode: revealed immediately upon selecting an option
    // In REAL_EXAM, A1, EXAM_50: answer revealed immediately if practice or at the end
    val isRevealed = when (mode) {
        ExamMode.LEARNING -> true
        ExamMode.PRACTICE, ExamMode.MISTAKES, ExamMode.TOPIC_BASED, ExamMode.SAVED -> isAnswered
        else -> isAnswered // Immediate feedback for training efficiency
    }

    val mistakeCount = questions.indices.count { idx ->
        val chosen = selectedAnswers[idx]
        chosen != null && chosen != questions[idx].correctOptionIndex
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = mode.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            text = if (mode == ExamMode.ALL_TICKETS) "$ticketNumber-bilet" else QuestionCategories.getCategoryName(currentQ.categoryId, currentLang == AppLanguage.RUSSIAN),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showFinishConfirmDialog = true }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Chiqish")
                    }
                },
                actions = {
                    // Star / Bookmark Button
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val newState = repository.toggleBookmark(currentQ.id)
                                isBookmarked = newState
                                snackbarHostState.showSnackbar(
                                    if (newState) "Savol tanlanganlarga saqlandi" else "Tanlanganlardan o'chirildi"
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Tanlangan",
                            tint = if (isBookmarked) BrandGold else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Report Question Button
                    IconButton(onClick = { showReportDialog = true }) {
                        Icon(Icons.Default.Flag, contentDescription = "Shikoyat qilish")
                    }

                    // Finish Exam Button
                    TextButton(onClick = { showFinishConfirmDialog = true }) {
                        Text(
                            text = "Yakunlash",
                            fontWeight = FontWeight.Bold,
                            color = BrandCrimson
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            if (currentIndex > 0) currentIndex -= 1
                        },
                        enabled = currentIndex > 0,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).testTag("prev_question_button")
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Oldingi")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Oldingi")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    if (currentIndex < questions.size - 1) {
                        Button(
                            onClick = {
                                currentIndex += 1
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).testTag("next_question_button")
                        ) {
                            Text("Keyingi")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ChevronRight, contentDescription = "Keyingi")
                        }
                    } else {
                        Button(
                            onClick = {
                                val correctCount = questions.indices.count { idx ->
                                    selectedAnswers[idx] == questions[idx].correctOptionIndex
                                }
                                val answersList = questions.indices.map { idx ->
                                    questions[idx] to (selectedAnswers[idx] ?: -1)
                                }
                                onFinishExam(questions.size, correctCount, elapsedSeconds, answersList)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandEmerald),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).testTag("finish_exam_submit_button")
                        ) {
                            Text("Natijani ko'rish", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Timer and Question Count Header
            item {
                ExamTimerBar(
                    remainingSeconds = remainingSeconds,
                    currentIndex = currentIndex,
                    totalQuestions = questions.size,
                    mistakeCount = mistakeCount,
                    onOpenQuestionsSheet = { showQuestionsSheet = true }
                )
            }

            // Question Text & Category Tag
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Mavzu: ${currentQ.categoryId}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            color = when (currentQ.difficulty) {
                                "EASY" -> BrandEmerald.copy(alpha = 0.15f)
                                "HARD" -> BrandCrimson.copy(alpha = 0.15f)
                                else -> BrandGold.copy(alpha = 0.15f)
                            },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = when (currentQ.difficulty) {
                                    "EASY" -> "Oson"
                                    "HARD" -> "Qiyin"
                                    else -> "O'rta"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = when (currentQ.difficulty) {
                                    "EASY" -> BrandEmerald
                                    "HARD" -> BrandCrimson
                                    else -> BrandGold
                                },
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val questionTitle = when (currentLang) {
                        AppLanguage.UZ_CYRILLIC -> currentQ.textUzCyrillic
                        AppLanguage.RUSSIAN -> currentQ.textRu
                        else -> currentQ.textUzLatin
                    }

                    Text(
                        text = "${currentIndex + 1}. $questionTitle",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Interactive Traffic Situation Diagram
            if (currentQ.diagramType != "NONE") {
                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        TrafficDiagramView(diagramType = currentQ.diagramType)
                    }
                }
            }

            // 4 Answer Options
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    val options = when (currentLang) {
                        AppLanguage.UZ_CYRILLIC -> currentQ.optionsUzCyrillic
                        AppLanguage.RUSSIAN -> currentQ.optionsRu
                        else -> currentQ.optionsUzLatin
                    }

                    options.forEachIndexed { optIdx, optText ->
                        val isSelected = selectedOption == optIdx
                        val isCorrectOption = optIdx == currentQ.correctOptionIndex

                        QuestionOptionCard(
                            optionIndex = optIdx,
                            optionText = optText,
                            isSelected = isSelected,
                            isCorrect = if (isRevealed) isCorrectOption else null,
                            isAnswerRevealed = isRevealed,
                            onClick = {
                                if (selectedOption == null || mode == ExamMode.LEARNING) {
                                    selectedAnswers = selectedAnswers + (currentIndex to optIdx)
                                    val isCorrect = optIdx == currentQ.correctOptionIndex
                                    coroutineScope.launch {
                                        repository.recordAnswer(currentQ.id, isCorrect)
                                    }
                                }
                            }
                        )
                    }
                }
            }

            // Detailed Explanation Panel (if revealed)
            if (isRevealed) {
                item {
                    val explanationText = when (currentLang) {
                        AppLanguage.UZ_CYRILLIC -> currentQ.explanationUzCyrillic
                        AppLanguage.RUSSIAN -> currentQ.explanationRu
                        else -> currentQ.explanationUzLatin
                    }

                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ExplanationCard(
                            explanation = explanationText,
                            lawReference = currentQ.lawReference
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Question Grid Bottom Sheet
    if (showQuestionsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showQuestionsSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Barcha savollar (${questions.size} ta)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(max = 350.dp)
                ) {
                    itemsIndexed(questions) { idx, q ->
                        val isCurrent = idx == currentIndex
                        val userChoice = selectedAnswers[idx]
                        val isAns = userChoice != null
                        val isCorr = userChoice == q.correctOptionIndex

                        val chipBg = when {
                            isCurrent -> MaterialTheme.colorScheme.primary
                            isAns && isCorr -> BrandEmerald
                            isAns && !isCorr -> BrandCrimson
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(chipBg)
                                .clickable {
                                    currentIndex = idx
                                    showQuestionsSheet = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                fontWeight = FontWeight.Bold,
                                color = if (isCurrent || isAns) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Report Question Dialog
    if (showReportDialog) {
        ReportQuestionDialog(
            questionId = currentQ.id,
            onDismiss = { showReportDialog = false },
            onSubmitReport = { reason ->
                showReportDialog = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Rahmat! Xabar mutaxassislarga yuborildi.")
                }
            }
        )
    }

    // Confirmation dialog before exiting early
    if (showFinishConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showFinishConfirmDialog = false },
            title = { Text("Imtihonni yakunlaysizmi?", fontWeight = FontWeight.Bold) },
            text = {
                val answeredCount = selectedAnswers.size
                Text("Siz ${questions.size} ta savoldan $answeredCount tasiga javob berdingiz. Imtihonni hoziroq yakunlab natijani ko'rishni xohlaysizmi?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showFinishConfirmDialog = false
                        val correctCount = questions.indices.count { idx ->
                            selectedAnswers[idx] == questions[idx].correctOptionIndex
                        }
                        val answersList = questions.indices.map { idx ->
                            questions[idx] to (selectedAnswers[idx] ?: -1)
                        }
                        onFinishExam(questions.size, correctCount, elapsedSeconds, answersList)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandEmerald)
                ) {
                    Text("Natijani ko'rish")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showFinishConfirmDialog = false
                    onBack()
                }) {
                    Text("Chiqish", color = BrandCrimson)
                }
            }
        )
    }
}
