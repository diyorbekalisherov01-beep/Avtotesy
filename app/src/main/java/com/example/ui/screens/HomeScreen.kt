package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.ExamMode
import com.example.data.model.UserRole
import com.example.data.repository.AnalyticsData
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    repository: YolnexRepository,
    onNavigateToExam: (ExamMode, Int, Int) -> Unit,
    onNavigateToTickets: () -> Unit,
    onNavigateToTopics: () -> Unit,
    onNavigateToSigns: () -> Unit,
    onNavigateToAiMarathon: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToFriends: () -> Unit,
    onNavigateToVideos: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onNavigateToSchools: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentLang by repository.currentLanguage.collectAsState()
    val currentRole by repository.currentRole.collectAsState()
    val userName by repository.userName.collectAsState()
    val isPremium by repository.isPremium.collectAsState()

    var analytics by remember { mutableStateOf<AnalyticsData?>(null) }
    var showLangDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        analytics = repository.getAnalyticsSummary()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // App Top Bar / Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(BrandCobalt),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.DirectionsCar,
                            contentDescription = "Yolnex Logo",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "YOLNEX",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            if (isPremium) {
                                Surface(
                                    color = BrandGold,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "Avto Test O'zbekiston",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Language Switcher Button
                    FilledTonalButton(
                        onClick = { showLangDialog = true },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(Icons.Default.Language, contentDescription = "Til", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = currentLang.label.take(3), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    if (currentRole == UserRole.ADMIN) {
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = onNavigateToAdmin,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.errorContainer)
                        ) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        // Hero Banner Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_banner),
                        contentDescription = "Yolnex Bosh Banner",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xDD0B1E36))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "DYHXX Rasmiy Imtihoniga Tayyorlaning",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "2022-yil 172-sonli VM qarori (LexUZ) asosida 1200+ savollar",
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onNavigateToExam(ExamMode.REAL_EXAM, 1, 1) },
                            colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            modifier = Modifier.height(36.dp).testTag("quick_exam_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Boshlash", tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Haqiqiy Imtihonni Boshlash", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Live Performance Stats Teaser
        item {
            analytics?.let { stat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatItem(
                            icon = Icons.Default.AssignmentTurnedIn,
                            value = "${stat.totalExams}",
                            label = "Testlar soni",
                            color = BrandCobalt
                        )
                        StatItem(
                            icon = Icons.Default.TrendingUp,
                            value = "${stat.passRate.toInt()}%",
                            label = "O'tish ko'rsatkichi",
                            color = BrandEmerald
                        )
                        StatItem(
                            icon = Icons.Default.Cancel,
                            value = "${stat.activeMistakes}",
                            label = "Faol xatolar",
                            color = BrandCrimson
                        )
                    }
                }
            }
        }

        // Primary Feature Quick-Launch Grid
        item {
            Text(
                text = "Asosiy Imtihon Bo'limlari",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModeCard(
                        title = "Haqiqiy Imtihon",
                        subtitle = "20 ta savol, 20 daqiqa",
                        icon = Icons.Default.Timer,
                        iconTint = BrandCobalt,
                        onClick = { onNavigateToExam(ExamMode.REAL_EXAM, 1, 1) },
                        modifier = Modifier.weight(1f)
                    )
                    ModeCard(
                        title = "Barcha Biletlar",
                        subtitle = "1 dan 60 gacha biletlar",
                        icon = Icons.Default.Book,
                        iconTint = BrandGold,
                        onClick = onNavigateToTickets,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModeCard(
                        title = "Xatolar Ustida Ishlash",
                        subtitle = "Avtomatik to'plangan",
                        icon = Icons.Default.ErrorOutline,
                        iconTint = BrandCrimson,
                        onClick = { onNavigateToExam(ExamMode.MISTAKES, 1, 1) },
                        modifier = Modifier.weight(1f)
                    )
                    ModeCard(
                        title = "Mavzulashtirilgan",
                        subtitle = "20 ta qoidalar guruhi",
                        icon = Icons.Default.Category,
                        iconTint = BrandEmerald,
                        onClick = onNavigateToTopics,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModeCard(
                        title = "PRO AI Marafon",
                        subtitle = "Zaif mavzular tahlili",
                        icon = Icons.Default.Psychology,
                        iconTint = BrandCyan,
                        onClick = onNavigateToAiMarathon,
                        modifier = Modifier.weight(1f)
                    )
                    ModeCard(
                        title = "A1 / Mototransport",
                        subtitle = "A1 toifasi imtihoni",
                        icon = Icons.Default.TwoWheeler,
                        iconTint = Color(0xFF8B5CF6),
                        onClick = { onNavigateToExam(ExamMode.A1_EXAM, 1, 1) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModeCard(
                        title = "50 talik Marafon",
                        subtitle = "Katta sinov imtihoni",
                        icon = Icons.Default.Flag,
                        iconTint = BrandCobalt,
                        onClick = { onNavigateToExam(ExamMode.EXAM_50, 1, 1) },
                        modifier = Modifier.weight(1f)
                    )
                    ModeCard(
                        title = "O'rganish Tartibi",
                        subtitle = "Javoblar darhol ochiq",
                        icon = Icons.Default.MenuBook,
                        iconTint = BrandEmerald,
                        onClick = { onNavigateToExam(ExamMode.LEARNING, 1, 1) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Academy & Ecosystem Row
        item {
            Text(
                text = "Qoidalar va Qo'shimcha Xizmatlar",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
            )
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                EcosystemRowItem(
                    title = "Yo'l Belgilari va Chiziqlari Katalogi",
                    description = "Barcha 7 toifadagi belgilar va chiziqlar ma'lumotnomasi",
                    icon = Icons.Default.Signpost,
                    tint = BrandGold,
                    onClick = onNavigateToSigns
                )
                EcosystemRowItem(
                    title = "Do'stlar Bilan 1v1 Musobaqa",
                    description = "Onlayn raqobat, jonli sinov va milliy reyting",
                    icon = Icons.Default.SportsEsports,
                    tint = BrandCyan,
                    onClick = onNavigateToFriends
                )
                EcosystemRowItem(
                    title = "Video Darsliklar Akademiyasi",
                    description = "Chorrahani kesib o'tish, estakada va amaliy haydash darslari",
                    icon = Icons.Default.SmartDisplay,
                    tint = BrandCrimson,
                    onClick = onNavigateToVideos
                )
                EcosystemRowItem(
                    title = "O'zbekiston Avtomaktablari Katalogi",
                    description = "Reytinglar, toifalar va bog'lanish kontaktlari",
                    icon = Icons.Default.School,
                    tint = BrandEmerald,
                    onClick = onNavigateToSchools
                )
                EcosystemRowItem(
                    title = "Premium Obuna (Payme / Click)",
                    description = "Cheksiz AI tahlili, to'liq offline baza va video darslar",
                    icon = Icons.Default.Diamond,
                    tint = BrandGold,
                    onClick = onNavigateToSubscription
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (showLangDialog) {
        AlertDialog(
            onDismissRequest = { showLangDialog = false },
            title = { Text(text = "Tilni tanlang / Выберите язык", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    AppLanguage.values().forEach { lang ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    repository.setLanguage(lang)
                                    showLangDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (currentLang == lang),
                                onClick = {
                                    repository.setLanguage(lang)
                                    showLangDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = lang.label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLangDialog = false }) {
                    Text("Yopish")
                }
            }
        )
    }
}

@Composable
fun StatItem(icon: ImageVector, value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ModeCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconTint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = iconTint, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun EcosystemRowItem(
    title: String,
    description: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = tint, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = description, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "O'tish", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
