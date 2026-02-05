package com.example.viikko1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import com.example.viikko1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit,
    onAddClick: () -> Unit = {},
    onNavigateCalendar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.tasks.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    val selectedTask by viewModel.selectedTask.collectAsState()
    val addTaskFlag by viewModel.addTaskDialogVisible.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 36.dp)
    ) {
        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        TopAppBar(
            title = { Text("My Tasks") },
            actions = {
                IconButton(onClick = onNavigateCalendar) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Calendar"
                    )
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onAddClick,
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Add Task")
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.sortByDueDate() },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text("Sort", maxLines = 1, softWrap = false, overflow = TextOverflow.Clip)
            }

            OutlinedButton(
                onClick = { viewModel.showAll() },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text("All", maxLines = 1, softWrap = false, overflow = TextOverflow.Clip)
            }

            OutlinedButton(
                onClick = { viewModel.filterByDone(false) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text("Active", maxLines = 1, softWrap = false, overflow = TextOverflow.Clip)
            }

            OutlinedButton(
                onClick = { viewModel.filterByDone(true) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text("Done", maxLines = 1, softWrap = false, overflow = TextOverflow.Clip)
            }
        }



        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items( tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onRemove = { viewModel.removeTask(task.id) },
                    onDetails = { onTaskClick(task.id) }
                )
            }
        }
    }

    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onDismiss = { viewModel.closeDialog() },
            onSave = { updated ->
                viewModel.updateTask(updated)
                viewModel.closeDialog()
            },
            onDelete = {
                viewModel.removeTask(it)
                viewModel.closeDialog()
            }
        )
    }

    if (addTaskFlag) {
        AddDialog(
            onClose = { viewModel.addTaskDialogVisible.value = false },
            onSave = { viewModel.addTask(it) }
        )
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onRemove: () -> Unit,
    onDetails: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Due: ${task.dueDate} | Priority: ${task.priority}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = onToggle,
                        modifier = Modifier.weight(1f)
                    ) {
                            Text(
                                if (task.done) "Undo" else "Done",
                                softWrap = false,
                                modifier = Modifier.fillMaxWidth(),
                                overflow = TextOverflow.Visible,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                            )
                        }

                        OutlinedButton(
                            onClick = onDetails,
                            modifier = Modifier.weight(1f)
                        ) {
                                Text(
                                    "Edit",
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Visible,
                                    textAlign = TextAlign.Center
                                )
                            }

                            TextButton(
                                onClick = onRemove,
                                modifier = Modifier
                                    .weight(1f, fill = false)
                                    .widthIn(min = 100.dp)
                            ) {
                                Text(
                                    "Remove",
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Visible,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { onToggle() }
                            )
                        }
                    }
                }
            }
        }

