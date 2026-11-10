package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.model.ExamResultEntity
import com.example.data.model.LeaderboardUser
import com.example.data.model.TeacherAssignmentItem
import com.example.data.repository.AnalyticsData
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var analytics by remember { mutableStateOf<AnalyticsData?>(null) }
    val resultsList by repository.getAllExamResultsFlow().collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        analytics = repository.getAnalyticsSummary()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Statistika va Natijalar", fontWeight = FontWeight.Bold) },
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
            analytics?.let { stat ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Umumiy ko'rsatkichlar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(14.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                StatItem(Icons.Default.CheckCircle, "${stat.passedExams}", "O'tilgan", BrandEmerald)
                                StatItem(Icons.Default.Cancel, "${stat.failedExams}", "Yiqilgan", Color(0xFFEF4444))
                                StatItem(Icons.Default.Percent, "${stat.passRate.toInt()}%", "Muvaffaqiyat", BrandCobalt)
                                StatItem(Icons.Default.LibraryBooks, "${stat.totalQuestions}", "Baza hajmi", BrandGold)
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "O'tgan imtihonlar tarixi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
                )
            }

            if (resultsList.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.History, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Hali birorta ham imtihon topshirilmadi", fontWeight = FontWeight.SemiBold)
                            Text("Imtihon topshiring va natijalaringiz shu yerda saqlanadi.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            } else {
                items(resultsList) { res ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (res.passed) BrandEmerald.copy(alpha = 0.15f) else Color(0x22EF4444)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    if (res.passed) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (res.passed) BrandEmerald else Color(0xFFEF4444),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = res.mode,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "To'g'ri: ${res.correctAnswers}/${res.totalQuestions} • Vaqt: ${res.durationSeconds} sek",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = if (res.passed) "O'tdi" else "Yiqildi",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (res.passed) BrandEmerald else Color(0xFFEF4444)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendCompetitionScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var roomCode by remember { mutableStateOf("YOL-8472") }
    var inputCode by remember { mutableStateOf("") }
    var inDuel by remember { mutableStateOf(false) }
    var myScore by remember { mutableStateOf(0) }
    var opponentScore by remember { mutableStateOf(0) }
    var countdown by remember { mutableStateOf(10) }
    var duelFinished by remember { mutableStateOf(false) }

    val leaderboard = remember {
        listOf(
            LeaderboardUser(1, "Jasur Bekmirzayev", "Toshkent", 980, "98%"),
            LeaderboardUser(2, "Sherzod Qodirov", "Samarqand", 950, "95%"),
            LeaderboardUser(3, "Madina Umarova", "Farg'ona", 920, "92%"),
            LeaderboardUser(4, "Diyorbek Alisherov", "Toshkent", 890, "89%"),
            LeaderboardUser(5, "Aziz Rahimov", "Buxoro", 860, "86%")
        )
    }

    LaunchedEffect(inDuel) {
        if (inDuel) {
            duelFinished = false
            myScore = 0
            opponentScore = 0
            countdown = 10
            while (countdown > 0) {
                delay(1000)
                countdown -= 1
                myScore += (1..2).random()
                opponentScore += (0..2).random()
            }
            duelFinished = true
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("1v1 Do'stlar Bilan Musobaqa", fontWeight = FontWeight.Bold) },
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
            // Live Duel Simulator Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.FlashOn, contentDescription = null, tint = BrandGold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tezkor Duel Xonasi", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (!inDuel) {
                            Text(text = "Sizning xona kodingiz: $roomCode", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = { inDuel = true },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Jangga Boshlash", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = if (!duelFinished) "Jang ketmoqda: $countdown soniya" else "Jang yakunlandi!",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (!duelFinished) BrandGold else BrandEmerald
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Siz", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("$myScore ball", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = BrandEmerald)
                                    }
                                    Text("VS", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = BrandGold)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Raqib (Online)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("$opponentScore ball", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = BrandCobalt)
                                    }
                                }

                                if (duelFinished) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = if (myScore >= opponentScore) "G'alaba qozondingiz! +50 XP" else "Raqib g'alaba qozondi. Qayta urinib ko'ring!",
                                        fontWeight = FontWeight.Bold,
                                        color = if (myScore >= opponentScore) BrandEmerald else Color(0xFFEF4444)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    TextButton(onClick = { inDuel = false }) {
                                        Text("Yangi jang")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // National Leaderboard
            item {
                Text(
                    text = "O'zbekiston Milliy Reytingi",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            items(leaderboard) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = when (user.rank) {
                                1 -> BrandGold
                                2 -> Color(0xFF94A3B8)
                                3 -> Color(0xFFB45309)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            shape = CircleShape,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${user.rank}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (user.rank <= 3) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(user.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(user.city, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("${user.score} ball", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val assignments = remember {
        listOf(
            TeacherAssignmentItem("a1", "1-guruh: Chorrahalar sinovi", "Guruh A-24", "Chorrahada harakatlanish", "Bugun 20:00", 25, 22, 91.5),
            TeacherAssignmentItem("a2", "Yo'l belgilari nazorat ishi", "Guruh B-18", "Yo'l belgilari", "Ertaga 18:00", 30, 18, 84.0),
            TeacherAssignmentItem("a3", "50 talik yakuniy marafon", "Guruh C-12", "Barcha mavzular", "15-noyabr", 20, 19, 88.5)
        )
    }

    var showCreateDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("O'qituvchi / Avtomaktab Paneli", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Orqaga")
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Vazifa qo'shish", tint = MaterialTheme.colorScheme.primary)
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
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "O'quv guruhlari ko'rsatkichi", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            StatItem(Icons.Default.Groups, "75 ta", "Talabalar", BrandCobalt)
                            StatItem(Icons.Default.TaskAlt, "88%", "O'rtacha ball", BrandEmerald)
                            StatItem(Icons.Default.Assignment, "3 ta", "Faol vazifalar", BrandGold)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Tayinlangan imtihon vazifalari",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(assignments) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Assignment, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(task.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Mavzu: ${task.categoryName} • ${task.groupName}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Topshirdi: ${task.completedCount} / ${task.assignedCount}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "O'rtacha: ${task.avgScore}%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandEmerald
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

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Yangi imtihon vazifasi biriktirish") },
            text = {
                Text("Guruh talabalari uchun bilet yoki mavzu bo'yicha nazorat sinovi belgilash.")
            },
            confirmButton = {
                Button(onClick = { showCreateDialog = false }) {
                    Text("Biriktirish")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Bekor qilish")
                }
            }
        )
    }
}
