package app.apti.villa.feature.test

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.apti.villa.domain.model.AnswerStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    attemptId: Long,
    onNavigateToResults: (Long) -> Unit,
    viewModel: ExamViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    val paletteGrey = Color(0xFF9CA3AF)
    val paletteRed = Color(0xFFEF4444)
    val paletteGreen = Color(0xFF10B981)
    val palettePurple = Color(0xFF8B5CF6)
    val palettePurpleDot = Color(0xFF6366F1)

    fun statusToColor(status: AnswerStatus): Color = when (status) {
        AnswerStatus.UNSEEN -> paletteGrey
        AnswerStatus.SEEN, AnswerStatus.SKIPPED -> paletteRed
        AnswerStatus.ANSWERED -> paletteGreen
        AnswerStatus.MARKED -> palettePurple
        AnswerStatus.ANSWERED_MARKED -> palettePurpleDot
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = state.test?.title ?: "Exam Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                        )
                        state.remainingTimeSec?.let { rem ->
                            val minutes = rem / 60
                            val seconds = rem % 60
                            val isUrgent = rem < 300 // < 5 mins
                            Text(
                                text = String.format("⏱ %02d:%02d remaining", minutes, seconds),
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isUrgent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                fontWeight = if (isUrgent) FontWeight.ExtraBold else FontWeight.Medium,
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.togglePalette(true) }) {
                        Icon(Icons.Default.Apps, contentDescription = "Question Palette")
                    }
                    Button(
                        onClick = { viewModel.toggleSubmitDialog(true) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text("Submit")
                    }
                },
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 6.dp,
                shadowElevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.clearResponse() },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text("Clear")
                        }
                        OutlinedButton(
                            onClick = { viewModel.markForReviewAndNext() },
                            modifier = Modifier.weight(1.5f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text("Mark for Review")
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.previousQuestion() },
                            enabled = state.currentIndex > 0,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous")
                        }
                        Button(
                            onClick = { viewModel.saveAndNext() },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text("Save & Next")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
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
            val item = state.currentItem
            if (item == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("No question available.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Question Header
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Question ${state.currentIndex + 1} of ${state.totalQuestions}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            state.test?.let { t ->
                                Text(
                                    text = "+${t.marksCorrect} / -${t.negativeMarks}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                    }

                    // Question Text Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            ),
                        ) {
                            Text(
                                text = item.question.text,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(18.dp),
                                lineHeight = MaterialTheme.typography.titleMedium.lineHeight * 1.2,
                            )
                        }
                    }

                    // Options List
                    items(item.question.options.size) { optIndex ->
                        val option = item.question.options[optIndex]
                        val isSelected = state.selectedOptionId == option.id
                        val letter = ('A'.code + optIndex).toChar().toString()

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.selectOption(option.id)
                                },
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.selectOption(option.id) },
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$letter.  ${option.text}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Question Palette Modal Sheet
    if (state.showPalette) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.togglePalette(false) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Question Palette",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )

                // Status Legends
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    PaletteLegendItem(color = paletteGreen, label = "Answered (${state.answeredCount})")
                    PaletteLegendItem(color = paletteRed, label = "Not Answered")
                    PaletteLegendItem(color = palettePurple, label = "Marked (${state.markedCount})")
                    PaletteLegendItem(color = paletteGrey, label = "Not Visited (${state.notVisitedCount})")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Question Numbers Grid
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 48.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(state.items.size) { index ->
                        val item = state.items[index]
                        val isCurrent = index == state.currentIndex
                        val color = statusToColor(item.answer.status)

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable {
                                    viewModel.jumpToQuestion(index)
                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }

    // Submit Confirmation Dialog
    if (state.showSubmitDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleSubmitDialog(false) },
            title = {
                Text("Submit Test Confirmation")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Are you sure you want to submit the exam?")

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        ),
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Text("Total Questions: ${state.totalQuestions}")
                            Text("Answered: ${state.answeredCount}", color = paletteGreen, fontWeight = FontWeight.Bold)
                            Text("Marked for Review: ${state.markedCount}", color = palettePurple)
                            Text("Unanswered: ${state.unansweredCount}", color = paletteRed, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (state.unansweredCount > 0) {
                        Text(
                            text = "⚠️ You still have ${state.unansweredCount} unanswered questions.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.submitExam { attemptId ->
                            onNavigateToResults(attemptId)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Text("Submit Now")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { viewModel.toggleSubmitDialog(false) }) {
                    Text("Continue Exam")
                }
            },
        )
    }
}

@Composable
private fun PaletteLegendItem(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
        )
    }
}
