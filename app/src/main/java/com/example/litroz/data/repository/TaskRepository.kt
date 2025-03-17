package com.example.litroz.data.repository

import androidx.lifecycle.LiveData
import com.example.litroz.data.dao.TaskDao
import com.example.litroz.data.model.Task
import com.example.litroz.network.TaskApiService

class TaskRepository(val taskDao: TaskDao, private val apiService: TaskApiService)  {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()


    // Sincronizar dados com a API
    suspend fun syncTasks() {
        val tasksToSync = taskDao.getUnsyncedTasks()

        tasksToSync.collect { tasks ->
            for (task in tasks) {
                if(task.isDeleted){
                    try {
                        apiService.deleteTask(task.id)
                        taskDao.deleteTask(task)
                    } catch (e: Exception) {
                        //Toast(e)
                    }
                } else if(task.syncStatus){
                    // Sincroniza as tarefas com o servidor, dependendo do status da tarefa (inserção, atualização, exclusão)
                    if (task.isNew) {
                        task.isNew = false
                        apiService.createTask(task)
                    } else {
                        apiService.updateTask(task.id, task)
                    }
                    // Marcar como sincronizado no banco local
                    task.syncStatus = true
                    taskDao.updateTask(task)
                }
            }
        }
    }

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
        // Sincronização com a API quando online
        syncTasks()
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
        // Sincronização com a API quando online
        syncTasks()
    }

    suspend fun delete() {
        // Sincronização com a API quando online
        syncTasks()
    }

}
