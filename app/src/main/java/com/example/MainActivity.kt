package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.ExamMode
import com.example.data.model.QuestionEntity
import com.example.data.repository.YolnexRepository
import com.example.ui.screens.*
import com.example.ui.theme.YolnexTheme
import kotlinx.coroutines.launch

sealed class Screen {
    object Home : Screen()
    data class Exam(val mode: ExamMode, val ticketNumber: Int = 1, val categoryId: Int = 1) : Screen()
    data class ExamResult(
        val mode: ExamMode,
        val totalQuestions: Int,
        val correctAnswers: Int,
        val durationSeconds: Int,
        val answers: List<Pair<QuestionEntity, Int>>
    ) : Screen()
    object Tickets : Screen()
    object Topics : Screen()
    object Signs : Screen()
    object AiMarathon : Screen()
    object Analytics : Screen()
    object Friends : Screen()
    object Videos : Screen()
    object Schools : Screen()
    object Subscription : Screen()
    object Admin : Screen()
    object Profile : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = YolnexRepository(applicationContext)

        setContent {
            YolnexTheme {
                val coroutineScope = rememberCoroutineScope()
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
                val screenStack = remember { mutableStateListOf<Screen>() }

                fun navigateTo(screen: Screen) {
                    screenStack.add(currentScreen)
                    currentScreen = screen
                }

                fun navigateBack() {
                    if (screenStack.isNotEmpty()) {
                        currentScreen = screenStack.removeAt(screenStack.size - 1)
                    } else {
                        currentScreen = Screen.Home
                    }
                }

                // Initialize Database on launch
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        repository.initializeDatabase()
                    }
                }

                val isRootScreen = currentScreen is Screen.Home ||
                        currentScreen is Screen.Tickets ||
                        currentScreen is Screen.Signs ||
                        currentScreen is Screen.Analytics ||
                        currentScreen is Screen.Profile

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (isRootScreen) {
                            NavigationBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .windowInsetsPadding(WindowInsets.navigationBars)
                            ) {
                                NavigationBarItem(
                                    selected = currentScreen is Screen.Home,
                                    onClick = { currentScreen = Screen.Home },
                                    icon = { Icon(if (currentScreen is Screen.Home) Icons.Filled.Home else Icons.Outlined.Home, contentDescription = "Asosiy") },
                                    label = { Text("Asosiy") },
                                    modifier = Modifier.testTag("nav_home")
                                )
                                NavigationBarItem(
                                    selected = currentScreen is Screen.Tickets,
                                    onClick = { currentScreen = Screen.Tickets },
                                    icon = { Icon(if (currentScreen is Screen.Tickets) Icons.Filled.MenuBook else Icons.Outlined.MenuBook, contentDescription = "Biletlar") },
                                    label = { Text("Biletlar") },
                                    modifier = Modifier.testTag("nav_tickets")
                                )
                                NavigationBarItem(
                                    selected = currentScreen is Screen.Signs,
                                    onClick = { currentScreen = Screen.Signs },
                                    icon = { Icon(if (currentScreen is Screen.Signs) Icons.Filled.Signpost else Icons.Outlined.Signpost, contentDescription = "Belgilar") },
                                    label = { Text("Belgilar") },
                                    modifier = Modifier.testTag("nav_signs")
                                )
                                NavigationBarItem(
                                    selected = currentScreen is Screen.Analytics,
                                    onClick = { currentScreen = Screen.Analytics },
                                    icon = { Icon(if (currentScreen is Screen.Analytics) Icons.Filled.BarChart else Icons.Outlined.BarChart, contentDescription = "Statistika") },
                                    label = { Text("Natijalar") },
                                    modifier = Modifier.testTag("nav_analytics")
                                )
                                NavigationBarItem(
                                    selected = currentScreen is Screen.Profile,
                                    onClick = { currentScreen = Screen.Profile },
                                    icon = { Icon(if (currentScreen is Screen.Profile) Icons.Filled.Person else Icons.Outlined.Person, contentDescription = "Profil") },
                                    label = { Text("Profil") },
                                    modifier = Modifier.testTag("nav_profile")
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (val screen = currentScreen) {
                            is Screen.Home -> {
                                HomeScreen(
                                    repository = repository,
                                    onNavigateToExam = { mode, ticket, cat ->
                                        navigateTo(Screen.Exam(mode, ticket, cat))
                                    },
                                    onNavigateToTickets = { navigateTo(Screen.Tickets) },
                                    onNavigateToTopics = { navigateTo(Screen.Topics) },
                                    onNavigateToSigns = { navigateTo(Screen.Signs) },
                                    onNavigateToAiMarathon = { navigateTo(Screen.AiMarathon) },
                                    onNavigateToAnalytics = { navigateTo(Screen.Analytics) },
                                    onNavigateToFriends = { navigateTo(Screen.Friends) },
                                    onNavigateToVideos = { navigateTo(Screen.Videos) },
                                    onNavigateToAdmin = { navigateTo(Screen.Admin) },
                                    onNavigateToSchools = { navigateTo(Screen.Schools) },
                                    onNavigateToSubscription = { navigateTo(Screen.Subscription) }
                                )
                            }
                            is Screen.Exam -> {
                                ExamScreen(
                                    mode = screen.mode,
                                    ticketNumber = screen.ticketNumber,
                                    categoryId = screen.categoryId,
                                    repository = repository,
                                    onFinishExam = { total, correct, durationSec, answers ->
                                        currentScreen = Screen.ExamResult(
                                            mode = screen.mode,
                                            totalQuestions = total,
                                            correctAnswers = correct,
                                            durationSeconds = durationSec,
                                            answers = answers
                                        )
                                    },
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.ExamResult -> {
                                ExamResultScreen(
                                    mode = screen.mode,
                                    totalQuestions = screen.totalQuestions,
                                    correctAnswers = screen.correctAnswers,
                                    durationSeconds = screen.durationSeconds,
                                    answersList = screen.answers,
                                    repository = repository,
                                    onRetake = {
                                        currentScreen = Screen.Exam(screen.mode)
                                    },
                                    onGoHome = {
                                        screenStack.clear()
                                        currentScreen = Screen.Home
                                    }
                                )
                            }
                            is Screen.Tickets -> {
                                BackHandler { navigateBack() }
                                TicketsScreen(
                                    repository = repository,
                                    onSelectTicket = { ticket ->
                                        navigateTo(Screen.Exam(ExamMode.ALL_TICKETS, ticketNumber = ticket))
                                    },
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Topics -> {
                                BackHandler { navigateBack() }
                                TopicCatalogScreen(
                                    repository = repository,
                                    onSelectTopic = { catId ->
                                        navigateTo(Screen.Exam(ExamMode.TOPIC_BASED, categoryId = catId))
                                    },
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Signs -> {
                                BackHandler { navigateBack() }
                                TrafficSignsScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.AiMarathon -> {
                                BackHandler { navigateBack() }
                                AiMarathonScreen(
                                    repository = repository,
                                    onStartAiExam = {
                                        navigateTo(Screen.Exam(ExamMode.PRO_AI_MARATHON))
                                    },
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Analytics -> {
                                BackHandler { navigateBack() }
                                AnalyticsScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Friends -> {
                                BackHandler { navigateBack() }
                                FriendCompetitionScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Videos -> {
                                BackHandler { navigateBack() }
                                VideoAcademyScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Schools -> {
                                BackHandler { navigateBack() }
                                DrivingSchoolsScreen(
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Subscription -> {
                                BackHandler { navigateBack() }
                                SubscriptionScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Admin -> {
                                BackHandler { navigateBack() }
                                AdminScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                            is Screen.Profile -> {
                                BackHandler { navigateBack() }
                                ProfileScreen(
                                    repository = repository,
                                    onBack = { navigateBack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
