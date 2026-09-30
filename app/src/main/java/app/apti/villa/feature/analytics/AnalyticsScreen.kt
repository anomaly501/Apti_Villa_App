package app.apti.villa.feature.analytics

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.apti.villa.R
import app.apti.villa.domain.engine.analytics.PerformanceEngine
import app.apti.villa.domain.model.AnalyticsDashboardData

@Composable
fun AnalyticsScreen(
    onNavigateToPracticeTopic: (Long) -> Unit = {},
    viewModel: AnalyticsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val dashboard = state.dashboard
    if (dashboard == null || dashboard.coreMetrics.totalAttempted == 0) {
        AnalyticsEmptyState()
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        val tabs = listOf("Topics", "Hints & Difficulty", "Study Activity")
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

        when (state.selectedTab) {
            0 -> TopicsTab(
                dashboard = dashboard,
                filter = state.topicFilter,
                onFilterSelected = { viewModel.setTopicFilter(it) },
                onTopicClick = { viewModel.selectTopicDetail(it) },
                onPracticeTopic = onNavigateToPracticeTopic,
            )
            1 -> HintsAndDifficultyTab(dashboard = dashboard)
            2 -> StudyActivityTab(dashboard = dashboard)
        }
    }

    // Topic Detail Dialog
    state.selectedTopicDetail?.let { topic ->
        AlertDialog(
            onDismissRequest = { viewModel.selectTopicDetail(null) },
            title = {
                Text(
                    text = topic.topicName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    topic.subjectName?.let {
                        Text(
                            text = "Subject: $it",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Status:", fontWeight = FontWeight.Medium)
                        Text(topic.statusLabel, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Total Attempted:")
                        Text("${topic.attempted} questions")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("All-Time Accuracy:")
                        Text("${topic.accuracy.toInt()}%")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Recent Accuracy (last 20):")
                        Text("${topic.recentAccuracy.toInt()}%")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Avg Speed per Question:")
                        Text("${topic.avgTimeSpentMs / 1000}s")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val topicId = topic.topicId
                    viewModel.selectTopicDetail(null)
                    onNavigateToPracticeTopic(topicId)
                }) {
                    Text("Practice Topic")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.selectTopicDetail(null) }) {
                    Text("Close")
                }
            },
        )
    }
}

@Composable
private fun TopicsTab(
    dashboard: AnalyticsDashboardData,
    filter: String,
    onFilterSelected: (String) -> Unit,
    onTopicClick: (PerformanceEngine.ClassifiedTopic) -> Unit,
    onPracticeTopic: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // KPI Summary Row
        item {
            KpiSummaryCards(dashboard = dashboard)
        }

        // Weak Topic Alert Banner
        if (dashboard.weakTopics.isNotEmpty()) {
            val weakest = dashboard.weakTopics.first()
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Focus Area: ${weakest.topicName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Recent accuracy is ${weakest.recentAccuracy.toInt()}% across ${weakest.attempted} attempts. Strengthen this topic to boost your overall score.",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onPracticeTopic(weakest.topicId) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                            ),
                            modifier = Modifier.align(Alignment.End),
                        ) {
                            Text("Practice Now")
                        }
                    }
                }
            }
        }

        // Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listOf(
                    "ALL" to "All (${dashboard.allClassifiedTopics.size})",
                    "WEAK" to "Weak (${dashboard.weakTopics.size})",
                    "STRONG" to "Strong (${dashboard.strongTopics.size})",
                    "DEVELOPING" to "Developing",
                ).forEach { (key, label) ->
                    FilterChip(
                        selected = filter == key,
                        onClick = { onFilterSelected(key) },
                        label = { Text(label) },
                    )
                }
            }
        }

        val filteredTopics = when (filter) {
            "WEAK" -> dashboard.weakTopics
            "STRONG" -> dashboard.strongTopics
            "DEVELOPING" -> dashboard.allClassifiedTopics.filter { it.status == PerformanceEngine.TopicMasteryStatus.DEVELOPING }
            else -> dashboard.allClassifiedTopics
        }

        items(filteredTopics) { topic ->
            TopicPerformanceCard(
                topic = topic,
                onClick = { onTopicClick(topic) },
            )
        }
    }
}

@Composable
private fun KpiSummaryCards(dashboard: AnalyticsDashboardData) {
    val core = dashboard.coreMetrics
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Overall Accuracy
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            ),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${core.accuracy.toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Overall Acc.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${core.totalCorrect}/${core.totalAttempted}",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        // First Attempt Accuracy
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
            ),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${core.firstAttemptAccuracy.toInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = "1st Attempt",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Retention",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        // Avg Speed
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
            ),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${core.avgTimeSpentMs / 1000}s",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = "Avg Speed",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "per question",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Composable
private fun TopicPerformanceCard(
    topic: PerformanceEngine.ClassifiedTopic,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = topic.topicName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    topic.subjectName?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                val (badgeBg, badgeText) = when (topic.status) {
                    PerformanceEngine.TopicMasteryStatus.WEAK -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
                    PerformanceEngine.TopicMasteryStatus.STRONG -> Color(0xFF1B5E20) to Color.White
                    PerformanceEngine.TopicMasteryStatus.DEVELOPING -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
                    PerformanceEngine.TopicMasteryStatus.NOT_ENOUGH_DATA -> MaterialTheme.colorScheme.surface to MaterialTheme.colorScheme.onSurface
                }

                Surface(
                    color = badgeBg,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = topic.statusLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = badgeText,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { (topic.recentAccuracy / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = when (topic.status) {
                    PerformanceEngine.TopicMasteryStatus.WEAK -> MaterialTheme.colorScheme.error
                    PerformanceEngine.TopicMasteryStatus.STRONG -> Color(0xFF2E7D32)
                    else -> MaterialTheme.colorScheme.primary
                },
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${topic.attempted} attempted · ${topic.correct} correct",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Speed: ${topic.avgTimeSpentMs / 1000}s",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun HintsAndDifficultyTab(dashboard: AnalyticsDashboardData) {
    val hints = dashboard.hintDependency
    val diffs = dashboard.difficultyStats

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Hint Dependency Distribution",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Shows how independently you solve practice questions",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    HintStatRow("Solved without hints", hints.solvedWithoutHint, Color(0xFF2E7D32))
                    HintStatRow("Solved after Hint 1", hints.solvedAfterHint1, Color(0xFF1976D2))
                    HintStatRow("Solved after Hint 2", hints.solvedAfterHint2, Color(0xFFF57C00))
                    HintStatRow("Solved after Hint 3+", hints.solvedAfterHint3Plus, Color(0xFFE64A19))
                    HintStatRow("Could not solve / Revealed", hints.couldNotSolve, MaterialTheme.colorScheme.error)
                }
            }
        }

        item {
            Text(
                text = "Accuracy by Question Difficulty",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        items(diffs) { stat ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = stat.difficulty,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "${stat.attempted} attempted (${stat.correct} correct)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    Text(
                        text = if (stat.attempted > 0) "${stat.accuracy.toInt()}%" else "—",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (stat.accuracy >= 70f) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@Composable
private fun HintStatRow(label: String, count: Int, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun StudyActivityTab(dashboard: AnalyticsDashboardData) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                ),
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "🔥",
                        style = MaterialTheme.typography.displayMedium,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${dashboard.currentStreak} Day Streak",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Text(
                            text = "${dashboard.activeDaysCount} total active study days",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Recent Study Days",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        if (dashboard.recentStudyDays.isEmpty()) {
            item {
                Text(
                    text = "No study sessions recorded yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            items(dashboard.recentStudyDays) { day ->
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
                        Column {
                            Text(
                                text = day.date,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${day.activeMs / 60000} mins active study",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }

                        Text(
                            text = "${day.questionsAttempted} questions (${day.correct} correct)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnalyticsEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                Icons.Filled.Info,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "No Analytics Yet",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Practice questions or take tests to unlock your accuracy breakdown, hint dependency distribution, weak topic alerts, and study streaks.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
