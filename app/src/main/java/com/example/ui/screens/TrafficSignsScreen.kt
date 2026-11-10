package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.data.local.RoadSignsSeedData
import com.example.data.model.AppLanguage
import com.example.data.model.RoadSignItem
import com.example.data.repository.YolnexRepository
import com.example.ui.theme.BrandCobalt
import com.example.ui.theme.BrandCrimson
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrafficSignsScreen(
    repository: YolnexRepository,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentLang by repository.currentLanguage.collectAsState()
    val isRu = currentLang == AppLanguage.RUSSIAN

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Barchasi") }
    var selectedSign by remember { mutableStateOf<RoadSignItem?>(null) }

    val categories = listOf("Barchasi", "Ogohlantiruvchi", "Imtiyoz", "Taqiqlovchi", "Buyuruvchi", "Axborot-ishora", "Servis")

    val filteredSigns = remember(searchQuery, selectedCategory) {
        RoadSignsSeedData.signs.filter { sign ->
            val matchCategory = selectedCategory == "Barchasi" || sign.category == selectedCategory
            val matchQuery = searchQuery.isEmpty() ||
                    sign.code.contains(searchQuery, ignoreCase = true) ||
                    sign.nameUz.contains(searchQuery, ignoreCase = true) ||
                    sign.nameRu.contains(searchQuery, ignoreCase = true)
            matchCategory && matchQuery
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Yo'l Belgilari Ma'lumotnomasi", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Orqaga")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                placeholder = { Text("Belgi raqami (2.1) yoki nomi bo'yicha qidirish...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Qidiruv") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Tozalash")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )

            // Category Filter Chips Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        } else null
                    )
                }
            }

            // Signs List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredSigns) { sign ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { selectedSign = sign },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Badge indicating shape and category color
                            val badgeColor = when (sign.shape) {
                                "OCTAGON_RED", "CIRCLE_RED", "TRIANGLE_RED", "TRIANGLE_RED_INVERTED" -> BrandCrimson
                                "DIAMOND_YELLOW" -> BrandGold
                                else -> BrandCobalt
                            }

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(badgeColor.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = sign.code,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = badgeColor
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isRu) sign.nameRu else sign.nameUz,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = sign.category,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Icon(Icons.Default.Info, contentDescription = "Tafsilot", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

    selectedSign?.let { sign ->
        AlertDialog(
            onDismissRequest = { selectedSign = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = sign.code,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = if (isRu) sign.nameRu else sign.nameUz, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Toifasi: ${sign.category}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isRu) sign.descriptionRu else sign.descriptionUz,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = BrandCobalt.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Gavel, contentDescription = null, tint = BrandCobalt, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Manba: LexUZ 172-son VM qarori, 1-ilova",
                                fontSize = 11.sp,
                                color = BrandCobalt,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedSign = null }) {
                    Text("Tushunarli")
                }
            }
        )
    }
}
