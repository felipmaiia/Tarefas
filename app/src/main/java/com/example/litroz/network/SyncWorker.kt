package com.example.litroz.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.litroz.data.dao.TaskDatabase
class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val taskDao = TaskDatabase.getDatabase(appContext).taskDao()
    private val taskService = RetrofitInstance.apiService

    override suspend fun doWork(): Result {
        // Verifique se há tarefas locais que precisam ser sincronizadas
        val unsyncedTasks = taskDao.getAllTasks().value?.filter { it.syncStatus == false }


        if (unsyncedTasks != null) {
            for (task in unsyncedTasks) {

                if(task.isDeleted){
                    try {
                        taskService.deleteTask(task.id)
                        taskDao.deleteTask(task)
                    } catch (e: Exception) {
                        return Result.retry()
                    }
                } else if(task.syncStatus){
                    // Sincroniza as tarefas com o servidor, dependendo do status da tarefa (inserção, atualização, exclusão)
                    if (task.isNew) {
                        task.isNew = false
                        taskService.createTask(task)
                    } else {
                        taskService.updateTask(task.id, task)
                    }
                    // Marcar como sincronizado no banco local
                    task.syncStatus = true
                    taskDao.updateTask(task)
                }

            }
            return Result.retry()
        }

        return Result.success()
    }
}