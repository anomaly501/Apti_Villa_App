package app.apti.villa.feature.inventory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.apti.villa.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToTest: (Long) -> Unit,
    viewModel: InventoryViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            val tabs = listOf("Questions", "Tests", "Learning")
            TabRow(
                selectedTabIndex = state.selectedTab,
                modifier = Modifier.fillMaxWidth(),
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = state.selectedTab == index,
                        onClick = { viewModel.selectTab(index) },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (state.selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            )
                        },
                    )
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val data = state.inventoryData
                when (state.selectedTab) {
                    0 -> QuestionsInventoryTab(
                        stats = data?.questionStats,
                        questions = state.filteredQuestions,
                        currentFilter = state.questionFilter,
                        onFilterSelected = { viewModel.setQuestionFilter(it) },
                    )
                    1 -> TestsInventoryTab(
                        testItems = data?.testItems ?: emptyList(),
                        onTestClick = onNavigateToTest,
                    )
                    2 -> LearningInventoryTab(
                        stats = data?.learningStats,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuestionsInventoryTab(
    stats: QuestionInventoryStats?,
    questions: List<InventoryQuestionItem>,
    currentFilter: String,
    onFilterSelected: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (stats != null) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.totalCount}",
                        label = "Total Questions",
                    )
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.attemptedCount}",
                        label = "Attempted",
                        color = MaterialTheme.colorScheme.primary,
                    )
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.unattemptedCount}",
                        label = "Unattempted",
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.bookmarkedCount}",
                        label = "Bookmarks",
                    )
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.difficultCount}",
                        label = "Difficult",
                    )
                    InventoryStatCard(
                        modifier = Modifier.weight(1f),
                        value = "${stats.needsRevisionCount}",
                        label = "Revision",
                    )
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                listOf(
                    "ALL" to "All",
                    "ATTEMPTED" to "Attempted",
                    "UNATTEMPTED" to "Unattempted",
                    "BOOKMARKED" to "Bookmarks",
                    "DIFFICULT" to "Difficult",
                ).forEach { (key, label) ->
                    FilterChip(
                        selected = currentFilter == key,
                        onClick = { onFilterSelected(key) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                    )
                }
            }
        }

        if (questions.isEmpty()) {
            item {
                Text(
                    text = "No questions found matching this filter.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 24.dp),
                )
            }
        } else {
            items(questions) { q ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    ),
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Q#${q.questionId} · ${q.topicName ?: "General"}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                if (q.isBookmarked) {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = "Bookmarked",
                                        tint = Color(0xFFFFB300),
                                        modifier = Modifier.size(16.dp),
                                    )
                                }
                                if (q.isDifficult) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.errorContainer,
                                        shape = RoundedCornerShape(4.dp),
                                    ) {
                                        Text(
                                            text = "Difficult",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onErrorContainer,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = q.text,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Difficulty: ${q.difficulty ?: "Unlabeled"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            val statusText = when {
                                !q.isAttempted -> "Unattempted"
                                q.isCorrect == true -> "Correct ✓"
                                else -> "Incorrect ✗"
                            }
                            val statusColor = when {
                                !q.isAttempted -> MaterialTheme.colorScheme.onSurfaceVariant
                                q.isCorrect == true -> Color(0xFF2E7D32)
                                else -> MaterialTheme.colorScheme.error
                            }
                            Text(
                                text = statusText,
                                style = MaterialTheme.typography.bodySmall,
                                color = statusColor,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TestsInventoryTab(
    testItems: List<TestInventoryItem>,
    onTestClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "${testItems.size} Total Tests in Local Inventory",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        if (testItems.isEmpty()) {
            item {
                Text(
                    text = "No tests imported yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            items(testItems) { test ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTestClick(test.testId) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    ),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = test.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                            )
                            test.durationMinutes?.let {
                                Text(
                                    text = "$it mins",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${test.examName ?: "Standard"} · ${test.questionCount} Questions",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Max Marks: ${test.maxScore?.toInt() ?: test.questionCount}",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Button(onClick = { onTestClick(test.testId) }) {
                                Text("Take Test")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LearningInventoryTab(
    stats: LearningInventoryStats?,
) {
    if (stats == null) return

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Learning Notebook Inventory",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                InventoryStatCard(
                    modifier = Modifier.weight(1f),
                    value = "${stats.topicsStudiedCount} / ${stats.totalTopicsCount}",
                    label = "Topics Studied",
                    color = MaterialTheme.colorScheme.primary,
                )
                InventoryStatCard(
                    modifier = Modifier.weight(1f),
                    value = "${stats.savedTricksCount}",
                    label = "Saved Tricks",
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                InventoryStatCard(
                    modifier = Modifier.weight(1f),
                    value = "${stats.revisionQueueCount}",
                    label = "Revision Queue",
                    color = MaterialTheme.colorScheme.tertiary,
                )
                InventoryStatCard(
                    modifier = Modifier.weight(1f),
                    value = "${stats.activeMistakesCount}",
                    label = "Active Mistakes",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        if (stats.topMistakeTopics.isNotEmpty()) {
            item {
                Text(
                    text = "Topics With Most Mistakes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            items(stats.topMistakeTopics) { topicMistake ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = topicMistake.topicName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text = "${topicMistake.mistakeCount} mistakes",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InventoryStatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color,
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
