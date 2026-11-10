package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.model.AppLanguage
import com.example.data.model.QuestionEntity
import com.example.data.model.UserRole
import com.example.data.repository.AnalyticsData
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCrimson
import com.example.ui.theme.BrandCyan
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var totalQuestionsCount by remember { mutableStateOf(0) }
    var showImportDialog by remember { mutableStateOf(false) }
    var showAddQuestionDialog by remember { mutableStateOf(false) }
    var importJsonText by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        totalQuestionsCount = repository.getQuestionCount()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Admin Boshqaruv Markazi", fontWeight = FontWeight.Bold) },
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
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Baza Holati va Huquqlar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Faol savollar soni: $totalQuestionsCount ta", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text("Yuridik asos: O'zbekiston Respublikasi VM 172-son qarori (LexUZ)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        statusMessage?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(it, fontSize = 12.sp, color = BrandEmerald, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Text("Baza Boshqaruvi", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { showAddQuestionDialog = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Yangi Savol", fontSize = 12.sp)
                    }

                    Button(
                        onClick = { showImportDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCobalt),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("JSON Import", fontSize = 12.sp)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            val json = repository.exportQuestionsJson()
                            statusMessage = "Eksport tayyor (${json.length} belgi)"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Savollar bazasini JSON eksport qilish")
                }
            }

            item {
                Text("Platforma Auditoriyasi", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
            }

            item {
                AdminStatRow("Jami ro'yxatdan o'tgan o'quvchilar", "14,820 nafar")
                AdminStatRow("Faol instruktor va o'qituvchilar", "340 nafar")
                AdminStatRow("Hamkor avtomaktablar", "48 ta")
                AdminStatRow("Shikoyat qilingan savollar", "0 ta (tekshirilgan)")
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text("Savollarni JSON orqali import qilish") },
            text = {
                Column {
                    Text("JSON formatida savollar to'plamini kiriting:", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = importJsonText,
                        onValueChange = { importJsonText = it },
                        modifier = Modifier.fillMaxWidth().height(150.dp),
                        placeholder = { Text("[{\"id\": 101, \"textUzLatin\": ...}]") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val imported = repository.importQuestionsJson(importJsonText)
                            showImportDialog = false
                            if (imported > 0) {
                                statusMessage = "Muvaffaqiyatli $imported ta savol import qilindi!"
                                totalQuestionsCount = repository.getQuestionCount()
                            } else {
                                statusMessage = "Import xatolik bilan yakunlandi."
                            }
                        }
                    }
                ) {
                    Text("Import qilish")
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("Bekor qilish")
                }
            }
        )
    }

    if (showAddQuestionDialog) {
        var newQText by remember { mutableStateOf("") }
        var newOpt1 by remember { mutableStateOf("") }
        var newOpt2 by remember { mutableStateOf("") }
        var newExplanation by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddQuestionDialog = false },
            title = { Text("Yangi savol qo'shish") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newQText,
                        onValueChange = { newQText = it },
                        label = { Text("Savol matni (O'zbekcha)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = newOpt1,
                        onValueChange = { newOpt1 = it },
                        label = { Text("To'g'ri javob (A)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = newOpt2,
                        onValueChange = { newOpt2 = it },
                        label = { Text("Noto'g'ri javob (B)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = newExplanation,
                        onValueChange = { newExplanation = it },
                        label = { Text("Qoida tushuntirishi (LexUZ)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newQText.isNotEmpty() && newOpt1.isNotEmpty()) {
                            coroutineScope.launch {
                                val nextId = repository.getQuestionCount() + 1
                                repository.addQuestion(
                                    QuestionEntity(
                                        id = nextId,
                                        ticketNumber = 1,
                                        ticketQuestionIndex = 20,
                                        categoryId = 1,
                                        textUzLatin = newQText,
                                        textUzCyrillic = newQText,
                                        textRu = newQText,
                                        optionsUzLatin = listOf(newOpt1, newOpt2, "Boshqa holat", "Ruxsat berilmaydi"),
                                        optionsUzCyrillic = listOf(newOpt1, newOpt2, "Бошқа ҳолат", "Рухсат берилмайди"),
                                        optionsRu = listOf(newOpt1, newOpt2, "Другой случай", "Не разрешается"),
                                        correctOptionIndex = 0,
                                        explanationUzLatin = newExplanation.ifEmpty { "172-son VM qarori" },
                                        explanationUzCyrillic = newExplanation.ifEmpty { "172-сон ВМ қарори" },
                                        explanationRu = newExplanation.ifEmpty { "ПКМ РУз №172" },
                                        lawReference = "LexUZ: 172-son VM qarori"
                                    )
                                )
                                totalQuestionsCount = repository.getQuestionCount()
                                statusMessage = "Savol qo'shildi (ID: #$nextId)"
                                showAddQuestionDialog = false
                            }
                        }
                    }
                ) {
                    Text("Saqlash")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddQuestionDialog = false }) {
                    Text("Bekor qilish")
                }
            }
        )
    }
}

@Composable
private fun AdminStatRow(label: String, value: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentRole by repository.currentRole.collectAsState()
    val currentLang by repository.currentLanguage.collectAsState()
    val userName by repository.userName.collectAsState()
    val offlineDownloaded by repository.offlinePackDownloaded.collectAsState()

    var showRoleDialog by remember { mutableStateOf(false) }
    var showLangDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Foydalanuvchi Profili", fontWeight = FontWeight.Bold) },
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
            // User Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(BrandCobalt),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = "Rol: ${currentRole.title}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            Text(text = "DYHXX Imtihon guruhi: 2026-A", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            item {
                Text("Sozlamalar va Rollar", fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            item {
                ProfileOptionRow(
                    icon = Icons.Default.Badge,
                    title = "Foydalanuvchi Roli",
                    subtitle = currentRole.title,
                    onClick = { showRoleDialog = true }
                )
                ProfileOptionRow(
                    icon = Icons.Default.Language,
                    title = "Ilova Tili",
                    subtitle = currentLang.label,
                    onClick = { showLangDialog = true }
                )
                ProfileSwitchRow(
                    icon = Icons.Default.CloudDownload,
                    title = "Offline Imtihon To'plami (1200+)",
                    subtitle = if (offlineDownloaded) "Barcha savollar xotirada saqlangan" else "Yuklab olinmagan",
                    checked = offlineDownloaded,
                    onCheckedChange = { repository.toggleOfflinePack(it) }
                )
                ProfileOptionRow(
                    icon = Icons.Default.Policy,
                    title = "Yo'l Harakati Qoidalari Qonunchiligi",
                    subtitle = "Vazirlar Mahkamasining 172-son qarori (LexUZ)",
                    onClick = {}
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    if (showRoleDialog) {
        AlertDialog(
            onDismissRequest = { showRoleDialog = false },
            title = { Text("Foydalanuvchi rolini tanlang") },
            text = {
                Column {
                    UserRole.values().forEach { role ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    repository.setRole(role)
                                    showRoleDialog = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = currentRole == role, onClick = {
                                repository.setRole(role)
                                showRoleDialog = false
                            })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(role.title, fontSize = 14.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRoleDialog = false }) { Text("Yopish") }
            }
        )
    }

    if (showLangDialog) {
        AlertDialog(
            onDismissRequest = { showLangDialog = false },
            title = { Text("Tilni tanlang") },
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
                            RadioButton(selected = currentLang == lang, onClick = {
                                repository.setLanguage(lang)
                                showLangDialog = false
                            })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(lang.label, fontSize = 14.sp)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLangDialog = false }) { Text("Yopish") }
            }
        )
    }
}

@Composable
private fun ProfileOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ProfileSwitchRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = BrandEmerald)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    }
}
