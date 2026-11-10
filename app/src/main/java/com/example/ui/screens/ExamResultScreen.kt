package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.ui.components.ExplanationCard
import com.example.ui.components.TrafficDiagramView
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCrimson
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamResultScreen(
    mode: ExamMode,
    totalQuestions: Int,
    correctAnswers: Int,
    durationSeconds: Int,
    answersList: List<Pair<QuestionEntity, Int>>, // Question to chosen index (-1 if none)
    repository: YolnexRepository,
    onRetake: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentLang by repository.currentLanguage.collectAsState()
    val incorrectAnswers = totalQuestions - correctAnswers

    // In official Uzbekistan DYHXX examination: 20 questions, max 2 mistakes allowed
    val isPassed = if (totalQuestions == 20) incorrectAnswers <= 2 else (correctAnswers.toDouble() / totalQuestions) >= 0.85
    val scorePercentage = if (totalQuestions > 0) (correctAnswers * 100.0 / totalQuestions).toInt() else 0

    var expandedQuestionIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        repository.saveExamResult(
            mode = mode,
            total = totalQuestions,
            correct = correctAnswers,
            durationSeconds = durationSeconds
        )
    }

    BackHandler {
        onGoHome()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Imtihon Natijasi", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onGoHome) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Bosh sahifa")
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
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onRetake,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).testTag("retake_exam_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Qayta topshirish")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Qayta topshirish")
                    }

                    Button(
                        onClick = onGoHome,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.weight(1f).testTag("go_home_button")
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Bosh sahifa")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Bosh sahifa")
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Outcome Hero Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPassed) BrandEmerald.copy(alpha = 0.12f) else BrandCrimson.copy(alpha = 0.12f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(if (isPassed) BrandEmerald else BrandCrimson),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPassed) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                contentDescription = if (isPassed) "Muvaffaqiyatli" else "Yiqildi",
                                tint = Color.White,
                                modifier = Modifier.size(44.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isPassed) "IMTIHONDAN O'TDINGIZ!" else "IMTIHONDAN O'TA OLMADINGIZ",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = if (isPassed) BrandEmerald else BrandCrimson
                        )

                        Text(
                            text = if (isPassed) "Tabriklaymiz! Siz DYHXX me'yorlariga javob berdingiz." else "Ruxsat etilgan maksimal xatolar soni: 2 ta. Qayta urinib ko'ring.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score metrics row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            MetricBox(title = "Natija", value = "$correctAnswers / $totalQuestions", tint = if (isPassed) BrandEmerald else BrandCrimson)
                            MetricBox(title = "Aniqlik", value = "$scorePercentage%", tint = MaterialTheme.colorScheme.primary)
                            val mins = durationSeconds / 60
                            val secs = durationSeconds % 60
                            MetricBox(title = "Vaqt", value = String.format("%02d:%02d", mins, secs), tint = BrandGold)
                        }
                    }
                }
            }

            // Question Review Header
            item {
                Text(
                    text = "Barcha savollar tahlili",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                )
            }

            // List of questions with expandable reviews
            itemsIndexed(answersList) { idx, pair ->
                val q = pair.first
                val userChoice = pair.second
                val isCorrect = userChoice == q.correctOptionIndex
                val isExpanded = expandedQuestionIndex == idx

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            expandedQuestionIndex = if (isExpanded) null else idx
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isCorrect) BrandEmerald else BrandCrimson),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            val qText = when (currentLang) {
                                AppLanguage.UZ_CYRILLIC -> q.textUzCyrillic
                                AppLanguage.RUSSIAN -> q.textRu
                                else -> q.textUzLatin
                            }
                            Text(
                                text = "${idx + 1}. $qText",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Batafsil"
                            )
                        }

                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(10.dp))

                            if (q.diagramType != "NONE") {
                                TrafficDiagramView(diagramType = q.diagramType)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            val options = when (currentLang) {
                                AppLanguage.UZ_CYRILLIC -> q.optionsUzCyrillic
                                AppLanguage.RUSSIAN -> q.optionsRu
                                else -> q.optionsUzLatin
                            }

                            options.forEachIndexed { optIdx, optText ->
                                val isOptCorrect = optIdx == q.correctOptionIndex
                                val isOptUserChoice = optIdx == userChoice

                                val optBg = when {
                                    isOptCorrect -> BrandEmerald.copy(alpha = 0.2f)
                                    isOptUserChoice && !isOptCorrect -> BrandCrimson.copy(alpha = 0.2f)
                                    else -> Color.Transparent
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(optBg, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${('A' + optIdx)}. $optText",
                                        fontSize = 12.sp,
                                        fontWeight = if (isOptCorrect || isOptUserChoice) FontWeight.Bold else FontWeight.Normal,
                                        color = when {
                                            isOptCorrect -> BrandEmerald
                                            isOptUserChoice && !isOptCorrect -> BrandCrimson
                                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                }
                            }

                            val explanationText = when (currentLang) {
                                AppLanguage.UZ_CYRILLIC -> q.explanationUzCyrillic
                                AppLanguage.RUSSIAN -> q.explanationRu
                                else -> q.explanationUzLatin
                            }

                            ExplanationCard(
                                explanation = explanationText,
                                lawReference = q.lawReference
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun MetricBox(title: String, value: String, tint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = tint)
        Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
