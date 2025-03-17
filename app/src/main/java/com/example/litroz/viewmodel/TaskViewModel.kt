package com.example.litroz.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.litroz.data.dao.TaskDatabase
import com.example.litroz.data.model.Task
import com.example.litroz.data.repository.TaskRepository
import com.example.litroz.network.RetrofitInstance
import com.example.litroz.network.SyncWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    // LiveData para acionar a exibição da caixa de diálogo
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean> get() = _showDialog


    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        val apiService = RetrofitInstance.apiService
        repository = TaskRepository(taskDao, apiService)
        allTasks = repository.allTasks
    }
    fun syncTasks() {
        viewModelScope.launch {
            repository.syncTasks()
            enqueueSyncWorker()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete()
        }
    }

    fun onActionClicked() {
        // Lógica de negócios, por exemplo, validação ou outras verificações
        _showDialog.value = true // Altera o LiveData para sinalizar que a caixa de diálogo deve ser exibida
    }

    private fun enqueueSyncWorker() {
        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(getApplication()).enqueue(syncWorkRequest)
    }
}
