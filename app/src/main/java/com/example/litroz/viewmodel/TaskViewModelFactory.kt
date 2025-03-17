package com.example.litroz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.litroz.data.repository.TaskRepository
class askViewModelFactory{}
/*class TaskViewModelFactory(
    private val taskRepository: TaskRepository
) : ViewModelProvider.Factory {

    // Corrigido o método 'create' para corresponder à assinatura correta
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/