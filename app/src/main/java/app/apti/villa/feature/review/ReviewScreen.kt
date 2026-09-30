package app.apti.villa.feature.review

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
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
import app.apti.villa.domain.model.*

@Composable
fun ReviewScreen(
    onNavigateToPractice: (Long) -> Unit,
    onNavigateToAi: ((Long) -> Unit)? = null,
    viewModel: ReviewViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val tabs = listOf(
        stringResource(R.string.tab_mistakes),
        stringResource(R.string.tab_revision),
        stringResource(R.string.tab_bookmarks),
    )

    Column(modifier = Modifier.fillMaxSize()) {
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
            0 -> MistakesTabContent(
                state = state,
                onFilterChanged = { viewModel.setMistakeFilter(it) },
                onStartPractice = { viewModel.startMistakePractice(onNavigateToPractice) },
                onDelete = { viewModel.deleteMistake(it) },
                onNavigateToAi = onNavigateToAi,
            )
            1 -> RevisionTabContent(
                state = state,
                onStartRevision = { viewModel.startRevisionPractice(onNavigateToPractice) },
            )
            2 -> BookmarksTabContent(
                state = state,
                onSubTabChanged = { viewModel.setBookmarkSubTab(it) },
                onStartPractice = { viewModel.startBookmarkedPractice(onNavigateToPractice) },
                onDeleteTrick = { viewModel.deleteSavedTrick(it) },
            )
        }
    }
}

@Composable
private fun MistakesTabContent(
    state: ReviewUiState,
    onFilterChanged: (String) -> Unit,
    onStartPractice: () -> Unit,
    onDelete: (Long) -> Unit,
    onNavigateToAi: ((Long) -> Unit)? = null,
) {
    val isShowingActive = state.mistakeFilter == "ACTIVE"
    val displayList = if (isShowingActive) state.activeMistakes else state.resolvedMistakes

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = isShowingActive,
                    onClick = { onFilterChanged("ACTIVE") },
                    label = { Text("Active (${state.activeMistakes.size})") },
                )
                FilterChip(
                    selected = !isShowingActive,
                    onClick = { onFilterChanged("RESOLVED") },
                    label = { Text("Resolved (${state.resolvedMistakes.size})") },
                )
            }
        }

        // Hero Button for Active Mistakes
        if (isShowingActive && state.activeMistakes.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                    ),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Mistake Book",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                        Text(
                            text = "Practice questions you previously got wrong. Getting a question right 2 times consecutively resolves it!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onStartPractice,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Practice All Mistakes (${state.activeMistakes.size})")
                        }
                    }
                }
            }
        }

        if (displayList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (isShowingActive) "🎉 No active mistakes! Keep practicing." else "No resolved mistakes yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        } else {
            items(displayList, key = { it.question.id }) { mistake ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text("Wrong ${mistake.wrongCount}x", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) },
                                )
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text("${mistake.consecutiveCorrect}/2 Correct") },
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (onNavigateToAi != null) {
                                    IconButton(onClick = { onNavigateToAi(mistake.question.id) }) {
                                        Icon(Icons.Default.Psychology, contentDescription = "Ask AI", tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                                IconButton(onClick = { onDelete(mistake.question.id) }) {
                                    Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = mistake.question.text,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RevisionTabContent(
    state: ReviewUiState,
    onStartRevision: () -> Unit,
) {
    val stats = state.boxStats

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Due today hero card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Spaced Repetition Schedule",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${state.dueRevisionItems.size} Questions due for review today",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onStartRevision,
                        enabled = state.dueRevisionItems.isNotEmpty(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Start Daily Revision")
                    }
                }
            }
        }

        // 5 Leitner Boxes
        item {
            Text(
                text = "Leitner Progress (Boxes 0 to 5)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BoxCard(box = "Box 0", interval = "Due now", count = stats.box0Count, modifier = Modifier.weight(1f))
                BoxCard(box = "Box 1", interval = "1 day", count = stats.box1Count, modifier = Modifier.weight(1f))
                BoxCard(box = "Box 2", interval = "3 days", count = stats.box2Count, modifier = Modifier.weight(1f))
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BoxCard(box = "Box 3", interval = "7 days", count = stats.box3Count, modifier = Modifier.weight(1f))
                BoxCard(box = "Box 4", interval = "14 days", count = stats.box4Count, modifier = Modifier.weight(1f))
                BoxCard(box = "Box 5", interval = "30 days", count = stats.box5Count, modifier = Modifier.weight(1f))
            }
        }

        // List of due questions
        if (state.dueRevisionItems.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Due Questions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            items(state.dueRevisionItems, key = { it.question.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            SuggestionChip(
                                onClick = {},
                                label = { Text("Box ${item.box}", fontWeight = FontWeight.Bold) },
                            )
                            if (item.reasons.isNotEmpty()) {
                                Text(
                                    text = item.reasons.joinToString(", "),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = item.question.text,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxCard(
    box: String,
    interval: String,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = box, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Text(text = "$count", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text(text = interval, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun BookmarksTabContent(
    state: ReviewUiState,
    onSubTabChanged: (Int) -> Unit,
    onStartPractice: () -> Unit,
    onDeleteTrick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // SubTabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(
                    selected = state.bookmarkSubTab == 0,
                    onClick = { onSubTabChanged(0) },
                    label = { Text("Questions (${state.bookmarkedQuestions.size})") },
                )
                FilterChip(
                    selected = state.bookmarkSubTab == 1,
                    onClick = { onSubTabChanged(1) },
                    label = { Text("Tricks Notebook (${state.savedTricks.size})") },
                )
            }
        }

        if (state.bookmarkSubTab == 0) {
            if (state.bookmarkedQuestions.isNotEmpty()) {
                item {
                    Button(
                        onClick = onStartPractice,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Practice Bookmarked Questions")
                    }
                }
            }

            if (state.bookmarkedQuestions.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                        Text("No bookmarked questions yet. Star questions during practice to bookmark them!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                    }
                }
            } else {
                items(state.bookmarkedQuestions, key = { it.question.id }) { bm ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(bm.tag.name, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(bm.question.text, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        } else {
            // Tricks Notebook
            if (state.savedTricks.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                        Text("No saved tricks yet. Tap the star icon on any trick card to save it to your notebook!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                    }
                }
            } else {
                items(state.savedTricks, key = { it.trick.id }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                        ),
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "⚡ ${item.trick.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                )
                                IconButton(onClick = { onDeleteTrick(item.trick.id) }) {
                                    Icon(Icons.Outlined.Delete, contentDescription = "Delete trick")
                                }
                            }
                            Text(
                                text = item.trick.description,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "From: ${item.question.text}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                            )
                        }
                    }
                }
            }
        }
    }
}
