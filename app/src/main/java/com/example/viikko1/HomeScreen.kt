package com.example.viikko1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.domain.Task
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    var newTitle by remember { mutableStateOf("") }

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
                    val nextId = (viewModel.tasks.maxOfOrNull { it.id } ?: 0) + 1
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
            items(viewModel.tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onRemove = { viewModel.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggle: () -> Unit,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(task.description, style = MaterialTheme.typography.bodySmall)
                Text("Due: ${task.dueDate} | Priority: ${task.priority}", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.width(8.dp))

            Button(onClick = onToggle) {
                Text(if (task.done) "Undo" else "Done")
            }

            Spacer(Modifier.width(8.dp))

            TextButton(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}
