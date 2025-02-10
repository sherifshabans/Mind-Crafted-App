package com.elsharif.mindcrafted.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Upsert
    suspend fun updateTask(task: Task)

    @Query("DELETE  FROM Task WHERE taskId =:taskId")
    suspend fun deleteTask(taskId: Int)


    @Query("DELETE  FROM Task WHERE taskSubjectId =:subjectId")
    suspend fun deleteTasksBySubjectId(subjectId: Int)

    @Query("SELECT * FROM Task WHERE taskId =:taskId")
    suspend  fun getTaskById(taskId:Int):Task?

    @Query("SELECT  *FROM Task WHERE taskSubjectId =:subjectId")
    suspend fun getTasksBySubjectId(subjectId: Int):Flow<List<Task>>


    @Query("SELECT  *FROM Task ")
    suspend fun getAllTasks():Flow<List<Task>>



}