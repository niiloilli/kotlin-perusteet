package com.example.viikko1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikko1.model.Task
import com.example.viikko1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TaskViewModel : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val allTasks = mutableListOf<Task>()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    val addTaskDialogVisible = MutableStateFlow<Boolean>(false)

    val detailDialogVisible = MutableStateFlow(false)

    init {
        allTasks.addAll(mockTasks)
        _tasks.value = allTasks.toList()
    }

    fun openTask(id: Int) {
        val task = _tasks.value.find { it.id == id }
        _selectedTask.value = task
    }
    fun addTask(task: Task) {
        allTasks.add(task)
        _tasks.value += task
        addTaskDialogVisible.value = false
    }

    fun toggleDone(id: Int) {
        allTasks.replaceAll {
            if (it.id == id) it.copy(done = !it.done) else it
        }
        _tasks.value = allTasks.toList()
    }

    fun removeTask(id: Int) {
        allTasks.removeAll { it.id == id }
        _tasks.value = allTasks.toList()
    }

    fun updateTask(updated: Task) {
        allTasks.replaceAll {
            if (it.id == updated.id) updated else it
        }
        _tasks.value = allTasks.toList()
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = allTasks.filter { it.done == done}
    }

    fun sortByDueDate() {
        _tasks.value = allTasks.sortedBy { it.dueDate }
    }

    fun closeDialog() {
        _selectedTask.value = null
        }

    fun showAll() {
        _tasks.value = allTasks.toList()
    }
}