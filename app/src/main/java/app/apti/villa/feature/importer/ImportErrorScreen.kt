package app.apti.villa.feature.importer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.apti.villa.R
import app.apti.villa.domain.importer.IssueSeverity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportErrorScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPreview: () -> Unit,
    viewModel: ImportViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val plan = uiState.plan
    val errors = plan?.issues?.filter { it.severity == IssueSeverity.ERROR } ?: emptyList()
    val skippedCount = plan?.skippedQuestions?.size ?: 0
    val validCount = plan?.validQuestions?.size ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Validation Issues") },
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
                if (validCount > 0) {
                    Button(
                        onClick = { viewModel.acceptValidOnlyAndProceed(onNavigateToPreview) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Import valid questions only (skip $skippedCount)")
                    }
                }
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                        Spacer(modifier = Modifier.padding(6.dp))
                        Column {
                            Text(
                                text = "Validation Failed — ${errors.size} error(s)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                            )
                            Text(
                                text = "$skippedCount question(s) cannot be imported. $validCount question(s) are valid.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(errors) { issue ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = if (issue.questionIndex != null) "Question #${issue.questionIndex + 1}" else "File Structure",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            val matchedSkipped = plan?.skippedQuestions?.find { it.index == issue.questionIndex }
                            if (matchedSkipped?.rawJson != null) {
                                TextButton(onClick = { viewModel.setInspectQuestion(matchedSkipped) }) {
                                    Text("Inspect")
                                }
                            }
                        }

                        Text(
                            text = issue.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Path: ${issue.path} [${issue.code}]",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = FontFamily.Monospace,
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Inspect Question Dialog
    uiState.inspectQuestion?.let { skipped ->
        AlertDialog(
            onDismissRequest = { viewModel.setInspectQuestion(null) },
            title = { Text("Inspect Question #${skipped.index + 1} (${skipped.id ?: "no id"})") },
            text = {
                val scroll = rememberScrollState()
                Column(modifier = Modifier.verticalScroll(scroll)) {
                    Text(
                        text = "Reason: ${skipped.reason}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = skipped.rawJson ?: "No JSON available",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                            .padding(8.dp),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.setInspectQuestion(null) }) {
                    Text("Close")
                }
            },
        )
    }
}
