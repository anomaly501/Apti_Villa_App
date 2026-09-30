package app.apti.villa.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.apti.villa.feature.question.QuestionScreen
import app.apti.villa.R

import app.apti.villa.feature.analytics.AnalyticsScreen
import app.apti.villa.feature.home.HomeScreen
import app.apti.villa.feature.onboarding.OnboardingScreen
import app.apti.villa.feature.practice.PracticeScreen
import app.apti.villa.feature.profile.ProfileEditScreen
import app.apti.villa.feature.profile.ProfileScreen
import app.apti.villa.feature.review.ReviewScreen
import app.apti.villa.feature.settings.SettingsScreen
import app.apti.villa.feature.inventory.InventoryScreen
import app.apti.villa.feature.search.SearchScreen
import app.apti.villa.feature.test.TestListScreen
import app.apti.villa.feature.backup.BackupScreen
import app.apti.villa.feature.settings.DataManagementScreen
import app.apti.villa.feature.privacy.PrivacyScreen
import app.apti.villa.feature.test.ExamScreen
import app.apti.villa.feature.test.TestResultScreen
import app.apti.villa.feature.test.TestReviewScreen
import app.apti.villa.feature.ai.AiSettingsScreen
import app.apti.villa.feature.ai.AiChatScreen
import app.apti.villa.feature.ai.AiHistoryScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AptiVillaNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine if we should show bottom bar (only on main tab screens)
    val showBottomBar = remember(currentDestination) {
        BottomTab.entries.any { tab ->
            currentDestination?.hasRoute(tab.route::class) == true
        }
    }

    // Determine if we should show top bar
    val showTopBar = showBottomBar && currentDestination?.hasRoute(Routes.Home::class) == true

    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = { },
                    actions = {
                        IconButton(onClick = {
                            navController.navigate(Routes.Search)
                        }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = stringResource(R.string.cd_search),
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(Routes.Profile)
                        }) {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = stringResource(R.string.cd_profile),
                            )
                        }
                    },
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BottomTab.entries.forEach { tab ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(tab.route::class)
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = stringResource(tab.labelResId),
                                )
                            },
                            label = { Text(stringResource(tab.labelResId)) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Onboarding,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(200)) },
            exitTransition = { fadeOut(tween(200)) },
        ) {
            // Onboarding
            composable<Routes.Onboarding> {
                OnboardingScreen(
                    onComplete = {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Onboarding) { inclusive = true }
                        }
                    },
                )
            }

            // Bottom tabs
            composable<Routes.Home> {
                HomeScreen(
                    onNavigateToImport = { navController.navigate(Routes.ImportSource) },
                    onNavigateToPractice = { navController.navigate(Routes.Practice) },
                    onNavigateToTests = { navController.navigate(Routes.Tests) },
                    onNavigateToReview = { navController.navigate(Routes.Review) },
                    onNavigateToInventory = { navController.navigate(Routes.Inventory) },
                    onNavigateToPracticeSession = { attemptId ->
                        navController.navigate(Routes.PracticeSession(attemptId = attemptId))
                    },
                )
            }
            composable<Routes.Practice> {
                PracticeScreen(
                    onNavigateToSession = { attemptId ->
                        navController.navigate(Routes.PracticeSession(attemptId = attemptId))
                    },
                    onNavigateToImport = {
                        navController.navigate(Routes.ImportSource)
                    },
                )
            }
            composable<Routes.PracticeSession> { backStackEntry ->
                val sessionRoute = backStackEntry.toRoute<Routes.PracticeSession>()
                QuestionScreen(
                    attemptId = sessionRoute.attemptId,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAi = { qId ->
                        navController.navigate(Routes.AiChat(questionId = qId))
                    },
                )
            }
            composable<Routes.Tests> {
                TestListScreen(
                    onNavigateToExam = { attemptId ->
                        navController.navigate(Routes.ExamScreen(attemptId = attemptId))
                    },
                    onNavigateToImport = {
                        navController.navigate(Routes.ImportSource)
                    },
                )
            }
            composable<Routes.ExamScreen> { backStackEntry ->
                val examRoute = backStackEntry.toRoute<Routes.ExamScreen>()
                ExamScreen(
                    attemptId = examRoute.attemptId,
                    onNavigateToResults = { attemptId ->
                        navController.navigate(Routes.TestResults(attemptId = attemptId)) {
                            popUpTo(Routes.Tests) { inclusive = false }
                        }
                    },
                )
            }
            composable<Routes.TestResults> { backStackEntry ->
                val resultRoute = backStackEntry.toRoute<Routes.TestResults>()
                TestResultScreen(
                    attemptId = resultRoute.attemptId,
                    onNavigateToReview = { attemptId ->
                        navController.navigate(Routes.TestReview(attemptId = attemptId))
                    },
                    onNavigateBackToTests = {
                        navController.navigate(Routes.Tests) {
                            popUpTo(Routes.Tests) { inclusive = true }
                        }
                    },
                )
            }
            composable<Routes.TestReview> { backStackEntry ->
                val reviewRoute = backStackEntry.toRoute<Routes.TestReview>()
                TestReviewScreen(
                    attemptId = reviewRoute.attemptId,
                    onNavigateBack = { navController.popBackStack() },
                )
            }


            composable<Routes.Review> {
                ReviewScreen(
                    onNavigateToPractice = { attemptId ->
                        navController.navigate(Routes.PracticeSession(attemptId = attemptId))
                    },
                    onNavigateToAi = { questionId ->
                        navController.navigate(Routes.AiChat(questionId = questionId))
                    },
                )
            }
            composable<Routes.Analytics> {
                AnalyticsScreen(
                    onNavigateToPracticeTopic = { topicId ->
                        navController.navigate(Routes.Practice)
                    },
                )
            }

            // Top-bar destinations
            composable<Routes.Profile> {
                ProfileScreen(
                    onNavigateToEdit = { navController.navigate(Routes.ProfileEdit) },
                    onNavigateToSettings = { navController.navigate(Routes.Settings) },
                    onNavigateToInventory = { navController.navigate(Routes.Inventory) },
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable<Routes.ProfileEdit> {
                ProfileEditScreen(
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable<Routes.Settings> {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToBackup = { navController.navigate(Routes.Backup) },
                    onNavigateToDataManagement = { navController.navigate(Routes.DataManagement) },
                    onNavigateToPrivacy = { navController.navigate(Routes.Privacy) },
                    onNavigateToAiSettings = { navController.navigate(Routes.AiSettings) },
                )
            }
            composable<Routes.Backup> {
                BackupScreen(
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable<Routes.DataManagement> {
                DataManagementScreen(
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable<Routes.Privacy> {
                PrivacyScreen(
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            composable<Routes.AiSettings> {
                AiSettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToHistory = { navController.navigate(Routes.AiHistory) },
                )
            }
            composable<Routes.AiHistory> {
                AiHistoryScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToChat = { questionId ->
                        navController.navigate(Routes.AiChat(questionId = questionId))
                    },
                )
            }
            composable<Routes.AiChat> {
                AiChatScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToSettings = { navController.navigate(Routes.AiSettings) },
                )
            }
            composable<Routes.Search> {
                SearchScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToQuestion = { questionId ->
                        navController.navigate(Routes.Practice)
                    },
                    onNavigateToTest = { testId ->
                        navController.navigate(Routes.Tests)
                    },
                    onNavigateToTopic = { topicId ->
                        navController.navigate(Routes.Practice)
                    },
                )
            }
            composable<Routes.Inventory> {
                InventoryScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToTest = { testId ->
                        navController.navigate(Routes.Tests)
                    },
                )
            }
            composable<Routes.ImportSource> { backStackEntry ->
                val importViewModel: app.apti.villa.feature.importer.ImportViewModel = androidx.hilt.navigation.compose.hiltViewModel(backStackEntry)
                app.apti.villa.feature.importer.ImportSourceScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToPreview = { navController.navigate(Routes.ImportPreview) },
                    onNavigateToError = { navController.navigate(Routes.ImportError) },
                    viewModel = importViewModel,
                )
            }
            composable<Routes.ImportPreview> {
                val parentEntry = remember(it) { navController.getBackStackEntry(Routes.ImportSource) }
                val importViewModel: app.apti.villa.feature.importer.ImportViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                app.apti.villa.feature.importer.ImportPreviewScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToReport = { navController.navigate(Routes.ImportReport) },
                    viewModel = importViewModel,
                )
            }
            composable<Routes.ImportError> {
                val parentEntry = remember(it) { navController.getBackStackEntry(Routes.ImportSource) }
                val importViewModel: app.apti.villa.feature.importer.ImportViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                app.apti.villa.feature.importer.ImportErrorScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToPreview = { navController.navigate(Routes.ImportPreview) },
                    viewModel = importViewModel,
                )
            }
            composable<Routes.ImportReport> {
                val parentEntry = remember(it) { navController.getBackStackEntry(Routes.ImportSource) }
                val importViewModel: app.apti.villa.feature.importer.ImportViewModel = androidx.hilt.navigation.compose.hiltViewModel(parentEntry)
                app.apti.villa.feature.importer.ImportReportScreen(
                    onDone = {
                        navController.navigate(Routes.Tests) {
                            popUpTo(Routes.Home) { inclusive = false }
                        }
                    },
                    viewModel = importViewModel,
                )
            }
        }
    }
}
