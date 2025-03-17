package com.example.litroz.network

import com.example.litroz.data.model.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {
    // Obter todas as tarefas
    @GET("todos")
    suspend fun getTasks(): List<Task> // Lista de tarefas do JSONPlaceholder

    // Criar uma nova tarefa
    @POST("todos")
    suspend fun createTask(@Body task: Task): Task

    // Atualizar uma tarefa existente
    @PUT("todos/{id}")
    suspend fun updateTask(@Path("id") taskId: Int, @Body task: Task): Task

    // Excluir uma tarefa
    @DELETE("todos/{id}")
    suspend fun deleteTask(@Path("id") taskId: Int): Response<Void>
}