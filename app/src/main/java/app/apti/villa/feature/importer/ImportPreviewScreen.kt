package app.apti.villa.feature.importer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.apti.villa.R
import app.apti.villa.domain.importer.IssueSeverity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ImportPreviewScreen(
    onNavigateBack: () -> Unit,
    onNavigateToReport: () -> Unit,
    viewModel: ImportViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val plan = uiState.plan
    val scrollState = rememberScrollState()

    val warnings = plan?.issues?.filter { it.severity == IssueSeverity.WARNING } ?: emptyList()
    val totalHints = plan?.validQuestions?.sumOf { it.hints.size } ?: 0
    val totalTricks = plan?.validQuestions?.sumOf { it.tricks.size } ?: 0
    val totalSolutions = plan?.validQuestions?.count { it.hasSolution } ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Import Preview") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.cd_navigate_back))
                    }
                },
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = {
                        viewModel.commitImport { onNavigateToReport() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isCommitting && plan != null && plan.validQuestions.isNotEmpty(),
                ) {
                    if (uiState.isCommitting) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.height(20.dp),
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text("Importing…")
                    } else {
                        Text("Import Test (${plan?.validQuestions?.size ?: 0} questions)")
                    }
                }

                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isCommitting,
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Metadata Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = plan?.sourceMetadata?.title ?: "Untitled",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Topic: ${plan?.sourceMetadata?.topic}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (!plan?.sourceMetadata?.subTopic.isNullOrBlank()) {
                        Text(
                            text = "Sub-topic: ${plan?.sourceMetadata?.subTopic}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    if (!plan?.sourceMetadata?.sourceUrl.isNullOrBlank()) {
                        Text(
                            text = "Source: ${plan?.sourceMetadata?.sourceUrl}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }

            // Stats breakdown
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    StatBadge(label = "Questions", count = plan?.validQuestions?.size ?: 0)
                    StatBadge(label = "Hints", count = totalHints)
                    StatBadge(label = "Tricks", count = totalTricks)
                    StatBadge(label = "Solutions", count = totalSolutions)
                }
            }

            // Warnings
            if (warnings.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = "${warnings.size} warning(s): Tap to review details if needed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                        )
                    }
                }
            }

            // Assign section
            Text(
                text = "Assign & Configure",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            // Test title
            OutlinedTextField(
                value = uiState.customTitle,
                onValueChange = viewModel::onCustomTitleChange,
                label = { Text("Test Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            // Exam selection
            Text(
                text = "Target Exam (Optional)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                uiState.availableExams.take(8).forEach { exam ->
                    FilterChip(
                        selected = uiState.selectedExamId == exam.id,
                        onClick = {
                            viewModel.onExamSelected(
                                if (uiState.selectedExamId == exam.id) null else exam.id
                            )
                        },
                        label = { Text(exam.name) },
                    )
                }
            }

            // Duration and Negative marking
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = uiState.durationMinutes?.toString() ?: "",
                    onValueChange = {
                        viewModel.onDurationMinutesChange(it.toIntOrNull())
                    },
                    label = { Text("Duration (min)") },
                    placeholder = { Text("Untimed") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )

                OutlinedTextField(
                    value = uiState.negativeMarks.toString(),
                    onValueChange = {
                        val d = it.toDoubleOrNull()
                        if (d != null && d >= 0.0) {
                            viewModel.onNegativeMarksChange(d)
                        }
                    },
                    label = { Text("Neg. Marks") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }
        }
    }
}

@Composable
private fun StatBadge(label: String, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$count",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
