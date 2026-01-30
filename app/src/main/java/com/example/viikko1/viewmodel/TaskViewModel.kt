package com.example.viikko1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikko1.model.Task
import com.example.viikko1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TaskViewModel : ViewModel() {

    private var allTasks: List<Task> = emptyList()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        allTasks = mockTasks
        _tasks.value = allTasks
    }

    fun addTask(task: Task) {
        allTasks = allTasks + task
        _tasks.value = allTasks
    }

    fun toggleDone(id: Int) {
        allTasks = allTasks.map {
            if (it.id == id) it.copy(done=!it.done) else it
        }
        _tasks.value=allTasks
    }

    fun removeTask(id: Int) {
        allTasks = allTasks.filterNot { it.id == id }
        _tasks.value = allTasks
    }

    fun updateTask(updated: Task) {
        allTasks = allTasks.map { t ->
            if (t.id == updated.id) updated else t
        }
        _tasks.value = allTasks
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = allTasks.filter { it.done == done}
    }

    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
    }

    fun showAll() {
        _tasks.value = allTasks
    }
}