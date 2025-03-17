package com.example.litroz.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.litroz.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(task: List<TaskApiResponse>)*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE syncStatus = 0")
    fun getUnsyncedTasks(): Flow<List<Task>>


    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table WHERE syncStatus = 0")
    fun getPendingTasks(): LiveData<List<Task>>

}