package com.example.viikko1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit,
    onNavigateHome: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()


    val grouped = tasks.groupBy { it.dueDate ?: "No date" }

    Column(
        modifier = Modifier
            .padding(top = 36.dp)
            .padding(16.dp)

    ) {

        TopAppBar(
            title = { Text("Calendar") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.Filled.List, contentDescription = "Go to list")
                }
            }
        )

        LazyColumn {
            grouped.forEach { (date, tasksOfDay) ->

                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                items(tasksOfDay) { task ->
                    CalendarTaskCard(
                        task = task,
                        onTaskClick = { id -> onTaskClick(id) }
                    )
                }
            }
        }
    }

    if (selectedTask != null) {
        DetailDialog(
            task = selectedTask!!,
            onDismiss = { viewModel.closeDialog() },
            onSave = { viewModel.updateTask(it) },
            onDelete = {
                viewModel.removeTask(it)
                viewModel.closeDialog()
            }
        )
    }
}

@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )

            if (task.description.isNotBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
