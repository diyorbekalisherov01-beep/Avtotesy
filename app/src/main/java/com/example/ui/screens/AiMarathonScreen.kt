package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.QuestionCategories
import com.example.data.model.ExamMode
import com.example.data.repository.AnalyticsData
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiMarathonScreen(
    repository: YolnexRepository,
    onStartAiExam: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var analytics by remember { mutableStateOf<AnalyticsData?>(null) }
    var activeMistakesCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        analytics = repository.getAnalyticsSummary()
        activeMistakesCount = analytics?.activeMistakes ?: 0
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Psychology, contentDescription = "AI", tint = BrandCyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("PRO AI Ta'lim Yordamchisi", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Orqaga")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // AI Analysis Status Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(BrandCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(26.dp))
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Shaxsiy O'qish Tahlili",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Foydalanuvchi sinov natijalari asosida",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Aniqlangan zaif mavzular:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        WeakTopicItem(
                            number = 6,
                            title = "Chorrahada harakatlanish",
                            advice = "O'ng tomondagi to'siq va teng ahamiyatli yo'llarda navbat qoidasiga e'tibor qarating."
                        )
                        WeakTopicItem(
                            number = 2,
                            title = "Yo'l belgilari (Imtiyoz va Taqiqlovchi)",
                            advice = "2.1 (Asosiy yo'l) va 3.27 (To'xtash taqiqlangan) belgilarining amal qilish zonasini takrorlang."
                        )
                        WeakTopicItem(
                            number = 5,
                            title = "Tartibga soluvchi ishoralari",
                            advice = "Qo'lning turli vaziyatlarida tramvay va relssiz transportlarning o'tish yo'nalishlarini eslab qoling."
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onStartAiExam,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Zaif Mavzular Bo'yicha AI Marafonini Boshlash",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // AI Generated Personalized Study Plan
            item {
                Text(
                    text = "Tavsiya etilgan 5 kunlik tayyorgarlik rejasi",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                PlanDayCard(day = "1-kun", task = "Chorrahalar (6-bo'lim) bo'yicha 30 ta savol yechish va diagrammalarni tahlil qilish")
                PlanDayCard(day = "2-kun", task = "Yo'l belgilari va chiziqlari katalogini to'liq ko'rib chiqish")
                PlanDayCard(day = "3-kun", task = "Tartibga soluvchi va svetofor qoidalari bo'yicha amaliy test")
                PlanDayCard(day = "4-kun", task = "To'plangan barcha xatolar ustida ishlash (Xatolar rejimi)")
                PlanDayCard(day = "5-kun", task = "20 daqiqalik Haqiqiy Imtihon rejimida 3 ta nazorat sinovini topshirish")
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun WeakTopicItem(number: Int, title: String, advice: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = BrandGold.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "$number-mavzu",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandGold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = advice, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PlanDayCard(day: String, task: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = day,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = task, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
    }
}
