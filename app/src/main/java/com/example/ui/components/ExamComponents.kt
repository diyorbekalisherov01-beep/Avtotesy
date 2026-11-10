package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCrimson
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold

@Composable
fun QuestionOptionCard(
    optionIndex: Int,
    optionText: String,
    isSelected: Boolean,
    isCorrect: Boolean?, // null if not yet revealed, true if correct, false if wrong
    isAnswerRevealed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val letter = when (optionIndex) {
        0 -> "A"
        1 -> "B"
        2 -> "C"
        else -> "D"
    }

    val borderColor by animateColorAsState(
        targetValue = when {
            isAnswerRevealed && isCorrect == true -> BrandEmerald
            isAnswerRevealed && isSelected && isCorrect == false -> BrandCrimson
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        },
        label = "borderColor"
    )

    val bgColor by animateColorAsState(
        targetValue = when {
            isAnswerRevealed && isCorrect == true -> BrandEmerald.copy(alpha = 0.15f)
            isAnswerRevealed && isSelected && isCorrect == false -> BrandCrimson.copy(alpha = 0.15f)
            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else -> MaterialTheme.colorScheme.surface
        },
        label = "bgColor"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(2.dp, borderColor, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("option_card_$optionIndex"),
        color = bgColor,
        tonalElevation = if (isSelected) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Badge circle
            val badgeBg = when {
                isAnswerRevealed && isCorrect == true -> BrandEmerald
                isAnswerRevealed && isSelected && isCorrect == false -> BrandCrimson
                isSelected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
            val badgeTextColor = if (isSelected || isAnswerRevealed) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(badgeBg),
                contentAlignment = Alignment.Center
            ) {
                if (isAnswerRevealed && isCorrect == true) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "To'g'ri javob",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isAnswerRevealed && isSelected && isCorrect == false) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Noto'g'ri javob",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = letter,
                        fontWeight = FontWeight.Bold,
                        color = badgeTextColor,
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = optionText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 21.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ExamTimerBar(
    remainingSeconds: Int,
    currentIndex: Int,
    totalQuestions: Int,
    mistakeCount: Int,
    onOpenQuestionsSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    val isTimeRunningOut = remainingSeconds < 120

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Question badge
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.clickable(onClick = onOpenQuestionsSheet)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.GridView,
                        contentDescription = "Savollar ro'yxati",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${currentIndex + 1} / $totalQuestions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Mistakes counter (DYHXX rule: max 2 mistakes)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Cancel,
                    contentDescription = "Xatolar",
                    tint = if (mistakeCount >= 2) BrandCrimson else BrandGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Xato: $mistakeCount/2",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (mistakeCount >= 2) BrandCrimson else MaterialTheme.colorScheme.onSurface
                )
            }

            // Timer Pill
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isTimeRunningOut) BrandCrimson.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Timer,
                        contentDescription = "Vaqt",
                        tint = if (isTimeRunningOut) BrandCrimson else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = timeFormatted,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (isTimeRunningOut) BrandCrimson else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Linear progress
        val progress = if (totalQuestions > 0) (currentIndex + 1).toFloat() / totalQuestions else 0f
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ExplanationCard(
    explanation: String,
    lawReference: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Tushuntirish",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Qoida tushuntirishi",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = explanation,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (lawReference.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = BrandCobalt.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Gavel,
                            contentDescription = "Qonuniy asos",
                            tint = BrandCobalt,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = lawReference,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandCobalt
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReportQuestionDialog(
    questionId: Int,
    onDismiss: () -> Unit,
    onSubmitReport: (String) -> Unit
) {
    var selectedReason by remember { mutableStateOf("Savol matnida noaniqlik mavjud") }
    val reasons = listOf(
        "Savol matnida noaniqlik mavjud",
        "To'g'ri javob noto'g'ri belgilangan",
        "Qonunchilik o'zgargan (eskirgan qoida)",
        "Rasm yoki chizmada xato bor",
        "Imlo yoki tarjima xatosi"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Savol yuzasidan xabar berish (ID: #$questionId)",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        },
        text = {
            Column {
                Text(
                    text = "Ekspertlarimiz ushbu savolni tekshirib chiqishadi:",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                reasons.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = reason }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (selectedReason == reason),
                            onClick = { selectedReason = reason }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = reason, fontSize = 13.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmitReport(selectedReason) }
            ) {
                Text("Yuborish")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Bekor qilish")
            }
        }
    )
}
