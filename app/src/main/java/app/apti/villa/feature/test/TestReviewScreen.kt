package app.apti.villa.feature.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.apti.villa.domain.model.QuestionReviewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestReviewScreen(
    attemptId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TestReviewViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Questions", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            val report = state.report
            if (report == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("No review data available.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Filter Chips Row
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            FilterChip(
                                selected = state.selectedFilter == ReviewFilter.ALL,
                                onClick = { viewModel.setFilter(ReviewFilter.ALL) },
                                label = { Text("All (${report.totalQuestions})") },
                            )
                            FilterChip(
                                selected = state.selectedFilter == ReviewFilter.CORRECT,
                                onClick = { viewModel.setFilter(ReviewFilter.CORRECT) },
                                label = { Text("Correct (${report.correctCount})") },
                            )
                            FilterChip(
                                selected = state.selectedFilter == ReviewFilter.WRONG,
                                onClick = { viewModel.setFilter(ReviewFilter.WRONG) },
                                label = { Text("Wrong (${report.wrongCount})") },
                            )
                            FilterChip(
                                selected = state.selectedFilter == ReviewFilter.UNATTEMPTED,
                                onClick = { viewModel.setFilter(ReviewFilter.UNATTEMPTED) },
                                label = { Text("Skipped (${report.unattemptedCount})") },
                            )
                        }
                    }

                    // Question Cards
                    items(state.filteredReviews, key = { it.question.id }) { item ->
                        QuestionReviewCard(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuestionReviewCard(item: QuestionReviewItem) {
    val statusColor = when {
        item.isCorrect -> Color(0xFF10B981)
        item.selectedOption != null -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }

    val statusText = when {
        item.isCorrect -> "Correct ✓"
        item.selectedOption != null -> "Incorrect ✕"
        else -> "Unattempted"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, statusColor.copy(alpha = 0.4f)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Q${item.position + 1}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text(statusText, fontWeight = FontWeight.Bold, color = statusColor) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = statusColor.copy(alpha = 0.12f),
                    ),
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.3f)),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.question.text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Options with highlighting
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item.question.options.forEachIndexed { idx, opt ->
                    val isUserSelection = item.selectedOption?.id == opt.id
                    val isCorrectOption = opt.isCorrect

                    val optBgColor = when {
                        isCorrectOption -> Color(0xFF10B981).copy(alpha = 0.12f)
                        isUserSelection -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    val optBorderColor = when {
                        isCorrectOption -> Color(0xFF10B981)
                        isUserSelection -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }

                    val letter = ('A'.code + idx).toChar().toString()

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(if (isCorrectOption || isUserSelection) 2.dp else 1.dp, optBorderColor),
                        color = optBgColor,
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "$letter.  ${opt.text}",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f),
                            )
                            if (isCorrectOption) {
                                Text(
                                    text = "Correct Answer",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF10B981),
                                )
                            } else if (isUserSelection) {
                                Text(
                                    text = "Your Choice",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }
                    }
                }
            }

            // Solution Steps
            if (item.question.solutionSteps.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Solution Steps:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(6.dp))
                item.question.solutionSteps.forEach { step ->
                    Text(
                        text = "${step.number}. ${step.action}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 2.dp),
                    )
                }
            }

            // Tricks
            if (item.question.tricks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                item.question.tricks.forEach { trick ->
                    Text(
                        text = "⚡ Shortcut: ${trick.name} — ${trick.description}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 2.dp),
                    )
                }
            }
        }
    }
}
