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
import com.example.data.model.DrivingSchoolItem
import com.example.data.model.VideoLessonItem
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoAcademyScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lessons = remember {
        listOf(
            VideoLessonItem("v1", "Chorrahada burilish va ustunlik qoidalari", "18:45", "Chorrahalar", true, "Akmal Ergashev (Oliy toifali instruktor)", "Teng huquqli va asosiy yo'l chorrahalarida xavfsiz harakatlanish.", 12400),
            VideoLessonItem("v2", "Taqiqlovchi va buyuruvchi belgilar tahlili", "24:10", "Yo'l belgilari", true, "Nodira Karimova", "3.27, 3.28 va 4.1.1 belgilarining amaliy tushuntirishi.", 8900),
            VideoLessonItem("v3", "Avtodromda 'Estakada' va 'Parallel parkovka'", "15:20", "Avtodrom", false, "Sanjar Mirzayev", "DYHXX amaliy imtihonida estakada mashqini 100% to'g'ri bajarish sirlari.", 15600),
            VideoLessonItem("v4", "Tartibga soluvchi (regulyator) ishoralari", "21:30", "Tartibga soluvchi", false, "Akmal Ergashev", "Politsiya xodimining barcha ishoralarini oson eslab qolish texnikasi.", 7200)
        )
    }

    var selectedLesson by remember { mutableStateOf<VideoLessonItem?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Video Darsliklar Akademiyasi", fontWeight = FontWeight.Bold) },
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
            item {
                Surface(
                    color = BrandCobalt.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.PlayCircle, contentDescription = null, tint = BrandCobalt, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Eng tajribali haydovchi-instruktorlar tomonidan tayyorlangan rasmiy video darslar.",
                            fontSize = 12.sp,
                            color = BrandCobalt
                        )
                    }
                }
            }

            items(lessons) { lesson ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { selectedLesson = lesson },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (lesson.isFree) BrandEmerald.copy(alpha = 0.15f) else BrandGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (lesson.isFree) Icons.Default.PlayArrow else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = if (lesson.isFree) BrandEmerald else BrandGold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = lesson.titleUz,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${lesson.category} • ${lesson.duration} • ${lesson.views} ko'rilgan",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                color = if (lesson.isFree) BrandEmerald else BrandGold,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (lesson.isFree) "BEPUL" else "PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Instruktor: ${lesson.instructor}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    selectedLesson?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedLesson = null },
            title = { Text(item.titleUz, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PlayCircleFilled, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(54.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(item.descriptionUz, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Davomiyligi: ${item.duration}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            },
            confirmButton = {
                Button(onClick = { selectedLesson = null }) {
                    Text("Darsni ko'rish")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedLesson = null }) {
                    Text("Yopish")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrivingSchoolsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val schools = remember {
        listOf(
            DrivingSchoolItem("s1", "Vatanparvar Avtomaktabi", "Toshkent sh., Chilonzor", "Muqimiy ko'chasi 42", "+998 71 200-11-22", 4.9, 320, 96, "2 800 000 so'm"),
            DrivingSchoolItem("s2", "AvtoTest Samarqand", "Samarqand sh.", "Registon ko'chasi 15", "+998 66 233-44-55", 4.8, 240, 94, "2 600 000 so'm"),
            DrivingSchoolItem("s3", "Toshkent Avto Haydovchilik Akademiyasi", "Toshkent sh., Yunusobod", "Amir Temur shoh ko'chasi 88", "+998 71 202-33-44", 4.9, 510, 98, "3 200 000 so'm"),
            DrivingSchoolItem("s4", "Farg'ona Avto Maktab", "Farg'ona sh.", "Al-Farg'oniy ko'chasi 7", "+998 73 222-10-00", 4.7, 180, 91, "2 400 000 so'm")
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Avtomaktablar Katalogi", fontWeight = FontWeight.Bold) },
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
            item {
                Surface(
                    color = BrandEmerald.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "O'zbekiston Respublikasi IIV DYHXX tomonidan sertifikatlangan yetakchi avtomaktablar.",
                        fontSize = 12.sp,
                        color = BrandEmerald,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            items(schools) { school ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.School, contentDescription = null, tint = BrandCobalt)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(school.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${school.region} • ${school.address}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "O'tish ko'rsatkichi: ${school.passRatePercent}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrandEmerald)
                            Text(text = school.priceRange, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
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
fun SubscriptionScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTier by remember { mutableStateOf("1_MONTH") }
    var showCheckoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("YOLNEX Premium Obuna", fontWeight = FontWeight.Bold) },
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
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Diamond, contentDescription = null, tint = BrandGold, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("PRO Ta'lim Imkoniyatlari", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("DYHXX imtihonidan bir urinishda o'tish uchun to'liq paket", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(16.dp))

                        PremiumFeatureRow("Cheksiz PRO AI Marafon va zaif mavzular tahlili")
                        PremiumFeatureRow("1200+ savollarni to'liq offline yuklab olish")
                        PremiumFeatureRow("Barcha Premium video darsliklar va amaliyotlar")
                        PremiumFeatureRow("1v1 jonli musobaqalar va do'stlar dueli")
                    }
                }
            }

            item {
                Text("Tarifni tanlang", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                TierCard(
                    title = "1 Oylik Premium",
                    price = "45 000 so'm",
                    subtitle = "Imtihonga tayyorgarlik ko'rish uchun eng mashhur tarif",
                    isSelected = selectedTier == "1_MONTH",
                    onClick = { selectedTier = "1_MONTH" }
                )
                TierCard(
                    title = "3 Oylik Kengaytirilgan",
                    price = "99 000 so'm",
                    subtitle = "Tejamkor: oyiga atigi 33 000 so'm",
                    isSelected = selectedTier == "3_MONTH",
                    onClick = { selectedTier = "3_MONTH" }
                )
                TierCard(
                    title = "Avtomaktab Biznes Paketi",
                    price = "450 000 so'm",
                    subtitle = "Butun guruh talabalari va instruktor paneli uchun",
                    isSelected = selectedTier == "SCHOOL",
                    onClick = { selectedTier = "SCHOOL" }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showCheckoutDialog = true },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                ) {
                    Text("To'lovga o'tish (Payme / Click)", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = { Text("To'lov tizimini tanlang", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Xavfsiz to'lov shlyuzlari orqali rasmiy to'lov:")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { showCheckoutDialog = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA5))
                    ) {
                        Text("Payme orqali to'lash", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { showCheckoutDialog = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0089D0))
                    ) {
                        Text("Click Evolution orqali to'lash", fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showCheckoutDialog = false }) {
                    Text("Yopish")
                }
            }
        )
    }
}

@Composable
private fun PremiumFeatureRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Check, contentDescription = null, tint = BrandEmerald, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 12.sp)
    }
}

@Composable
private fun TierCard(
    title: String,
    price: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) BrandCobalt.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = isSelected, onClick = onClick)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(price, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}
