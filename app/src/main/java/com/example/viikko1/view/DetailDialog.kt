package com.example.viikko1.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko1.model.Task

@Composable

fun DetailDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit,
    onDelete: (Int) -> Unit
) {
    var title by remember(task.id) { mutableStateOf(task.title) }
    var description by remember(task.id) { mutableStateOf(task.description) }
    var dueDate by remember(task.id) { mutableStateOf(task.dueDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Description") })

            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    task.copy(
                        title = title,
                        description = description,
                        dueDate = dueDate
                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = { onDelete(task.id) }) {
                    Text("Delete")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}

