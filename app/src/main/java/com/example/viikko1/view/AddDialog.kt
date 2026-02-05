package com.example.viikko1.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.model.Task
import com.example.viikko1.viewmodel.TaskViewModel


@Composable
fun AddDialog(viewModel: TaskViewModel = viewModel(), onClose: () -> Unit, onSave: (Task) -> Unit) {
    var title by remember { mutableStateOf("title") }
    var description by remember { mutableStateOf("description") }
    var dueDate by remember { mutableStateOf("dueDate") }
    var priorityText by remember { mutableStateOf("priority") }
    var task by remember {
        mutableStateOf(Task(id = 0, title = "", description = "", priority = 0, dueDate = "", done = false))
    }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Add Task") },
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
                        dueDate = dueDate,
                        priority = priorityText.toIntOrNull() ?: task.priority
                    )
                )
                viewModel.addTask(task)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancel")
            }
        }
    )
}