package app.apti.villa.feature.question

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.apti.villa.core.common.ExternalLinkLauncher
import app.apti.villa.domain.model.Hint
import app.apti.villa.domain.model.QuestionOption
import app.apti.villa.domain.model.Trick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    attemptId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToAi: ((Long) -> Unit)? = null,
    viewModel: QuestionViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(attemptId) {
        if (attemptId > 0) {
            viewModel.loadSession(attemptId)
        }
    }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        val current = if (state.totalQuestions > 0) state.currentIndex + 1 else 0
                        Text(
                            text = "Question $current / ${state.totalQuestions}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        val minutes = state.questionElapsedSec / 60
                        val seconds = state.questionElapsedSec % 60
                        Text(
                            text = String.format("⏱ %02d:%02d", minutes, seconds),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    state.currentItem?.let { item ->
                        IconButton(onClick = { viewModel.toggleDifficult() }) {
                            Icon(
                                imageVector = if (item.question.isDifficult) Icons.Filled.Warning else Icons.Outlined.Warning,
                                contentDescription = "Mark Difficult",
                                tint = if (item.question.isDifficult) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = { viewModel.toggleBookmark() }) {
                            Icon(
                                imageVector = if (item.question.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (item.question.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        IconButton(onClick = { viewModel.showTagDialog(true) }) {
                            Icon(Icons.AutoMirrored.Filled.Label, contentDescription = "Tags")
                        }
                        if (onNavigateToAi != null) {
                            IconButton(onClick = { onNavigateToAi(item.question.id) }) {
                                Icon(
                                    Icons.Default.Psychology,
                                    contentDescription = "Ask AI",
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                },
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 6.dp,
                shadowElevation = 8.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedButton(
                        onClick = { viewModel.previousQuestion() },
                        enabled = state.hasPrevious,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Previous")
                    }

                    if (state.hasNext) {
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            shape = RoundedCornerShape(12.dp),
                        ) {
                            Text("Next")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    } else {
                        Button(
                            onClick = { viewModel.finishSession(onNavigateBack) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Finish Practice")
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
                    // Metadata Badges (Difficulty, Format)
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            item.question.difficulty?.let { diff ->
                                val diffColor = when (diff.lowercase()) {
                                    "easy" -> Color(0xFF10B981)
                                    "medium" -> Color(0xFFF59E0B)
                                    "hard" -> Color(0xFFEF4444)
                                    else -> MaterialTheme.colorScheme.primary
                                }
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(diff, fontWeight = FontWeight.Bold, color = diffColor) },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = diffColor.copy(alpha = 0.12f),
                                    ),
                                    border = BorderStroke(1.dp, diffColor.copy(alpha = 0.3f)),
                                )
                            }
                            if (item.question.language != null) {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(item.question.language.uppercase()) },
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
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
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
                    item {
                        Text(
                            text = "Options",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }

                    items(item.question.options.size) { optIndex ->
                        val option = item.question.options[optIndex]
                        val isSelected = item.answer.selectedOptionId == option.id
                        val isSolved = item.answer.isCorrect == true
                        val isRevealed = item.answer.revealed
                        val hasAnswered = isSolved || isRevealed

                        val isWrongSelection = isSelected && !option.isCorrect && (item.answer.isCorrect == false || hasAnswered)
                        val isCorrectOption = option.isCorrect && hasAnswered

                        val borderColor = when {
                            isCorrectOption -> Color(0xFF10B981)
                            isWrongSelection -> MaterialTheme.colorScheme.error
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.outlineVariant
                        }

                        val bgColor = when {
                            isCorrectOption -> Color(0xFF10B981).copy(alpha = 0.12f)
                            isWrongSelection -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                            isSelected -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val letter = ('A'.code + optIndex).toChar().toString()

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !hasAnswered) {
                                    viewModel.selectOption(option)
                                },
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(if (isSelected || isCorrectOption) 2.dp else 1.dp, borderColor),
                            colors = CardDefaults.cardColors(containerColor = bgColor),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(borderColor.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    when {
                                        isCorrectOption -> Icon(Icons.Outlined.Check, contentDescription = null, tint = Color(0xFF10B981))
                                        isWrongSelection -> Icon(Icons.Outlined.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                                        else -> Text(letter, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Text(
                                    text = option.text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }

                    // Feedback Banner & Actions if wrong
                    if (item.answer.isCorrect == false && !item.answer.revealed) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                                ),
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "Not quite right! Try another option or unlock hints below.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Button(
                                            onClick = { viewModel.revealAnswer() },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.error
                                            ),
                                            shape = RoundedCornerShape(10.dp),
                                        ) {
                                            Text("Show Answer")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Progressive Hint Ladder
                    if (item.question.hints.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "💡 Progressive Hints (${item.answer.hintsOpened}/${item.question.hints.size})",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        items(item.question.hints.size) { hintIndex ->
                            val hint = item.question.hints[hintIndex]
                            val isUnlocked = hint.number <= item.answer.hintsOpened
                            val isNextToUnlock = hint.number == item.answer.hintsOpened + 1

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isUnlocked) {
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    }
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    if (isUnlocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant
                                ),
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            text = "Hint ${hint.number}: ${hint.title}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        if (isUnlocked) {
                                            Text("Unlocked ✓", style = MaterialTheme.typography.labelSmall, color = Color(0xFF10B981))
                                        }
                                    }

                                    if (isUnlocked) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = hint.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    } else if (isNextToUnlock) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Button(
                                            onClick = { viewModel.unlockNextHint() },
                                            shape = RoundedCornerShape(10.dp),
                                        ) {
                                            Icon(Icons.Outlined.Lightbulb, contentDescription = null)
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("Unlock Hint ${hint.number}")
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "🔒 Unlock Hint ${hint.number - 1} first",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Solution Steps (Shown when solved or revealed)
                    if ((item.answer.isCorrect == true || item.answer.revealed) && item.question.solutionSteps.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "📝 Step-by-Step Solution",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        items(item.question.solutionSteps.size) { stepIndex ->
                            val step = item.question.solutionSteps[stepIndex]
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                ),
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.Top,
                                ) {
                                    Badge(containerColor = MaterialTheme.colorScheme.primary) {
                                        Text("${step.number}")
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = step.action,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }

                    // Tricks & Shortcuts (Shown when solved, revealed, or available)
                    if (item.question.tricks.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "⚡ Tricks & Shortcuts",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                        items(item.question.tricks.size) { trickIndex ->
                            val trick = item.question.tricks[trickIndex]
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f),
                                ),
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            text = trick.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        IconButton(onClick = { viewModel.toggleSaveTrick(trick) }) {
                                            Icon(
                                                imageVector = if (trick.isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                                                contentDescription = "Save trick",
                                                tint = if (trick.isSaved) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                    Text(
                                        text = trick.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                    }

                    // External Video Explanation Button (Optional online feature)
                    item.question.sourceUrl?.let { url ->
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = {
                                    val opened = ExternalLinkLauncher.openVideoUrl(
                                        context = context,
                                        url = url,
                                        timestampSec = item.question.sourceTimestampSec,
                                    )
                                    if (!opened) {
                                        Toast.makeText(context, "Internet connection required to watch video", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Icon(Icons.Default.PlayCircle, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                val tsText = item.question.sourceTimestampSec?.let { " (${it / 60}:${String.format("%02d", it % 60)})" } ?: ""
                                Text("Watch Video Explanation$tsText ▶")
                            }
                        }
                    }
                }
            }
        }
    }

    // Tag Selection Modal Dialog
    if (state.showTagDialog) {
        val currentItem = state.currentItem
        AlertDialog(
            onDismissRequest = { viewModel.showTagDialog(false) },
            title = { Text("Tags & Bookmarks") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.allTags.forEach { tag ->
                        val isAttached = currentItem?.question?.tags?.any { it.id == tag.id } == true
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.toggleTag(tag.id)
                                }
                                .padding(vertical = 4.dp),
                        ) {
                            Checkbox(
                                checked = isAttached,
                                onCheckedChange = { viewModel.toggleTag(tag.id) },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tag.name,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.showTagDialog(false) }) {
                    Text("Done")
                }
            },
        )
    }
}
