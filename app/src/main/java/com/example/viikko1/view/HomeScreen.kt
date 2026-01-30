package com.example.viikko1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import com.example.viikko1.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.tasks.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                placeholder = {
                    Text(
                        text = "Title for new task",
                        color = Color.Black
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedLabelColor = Color.Black,
                    cursorColor = Color.Black
                ),
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                val title = newTitle.trim()
                if (title.isNotEmpty()) {
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    viewModel.addTask(
                        Task(
                            id = nextId,
                            title = title,
                            description = "Created from UI",
                            priority = 2,
                            dueDate = "2026-01-20",
                            done = false
                        )
                    )
                    newTitle = ""
                }
            }) {
                Text("Add")
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
                    onDetails = { selectedTask = task }
                )
            }
        }
    }

    if (selectedTask != null) {
        DetailScreen(
            task = selectedTask!!,
            onDismiss = { selectedTask = null },
            onSave = { updated ->
                viewModel.updateTask(updated)
                selectedTask = null
            },
            onDelete = { id ->
                viewModel.removeTask(id)
                selectedTask = null
            }
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onToggle,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (task.done) "Undo" else "Done", maxLines = 1)
                    }

                    OutlinedButton(
                        onClick = onDetails,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Details", maxLines = 1)
                    }

                    TextButton(
                        onClick = onRemove,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Remove", maxLines = 1)
                    }
                }
            }
        }
    }
}
