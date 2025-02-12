package com.elsharif.mindcrafted.data.repository

import com.elsharif.mindcrafted.data.local.TaskDao
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.domain.repository.TaskRepository
import com.elsharif.mindcrafted.tasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl@Inject constructor(
    private val taskDao: TaskDao
):TaskRepository {
    override suspend fun upsertTask(task: Task) {
     taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTasksForSubject(subjectInt: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasksForSubject(subjectInt: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllUpcomingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().
                map { tasks->tasks.filter { it.isComplete.not() } }
               .map { tasks -> sortTasks(tasks) }
    }
    private fun sortTasks(tasks:List<Task>):List<Task>{
        return tasks.sortedWith(compareBy<Task>{it.dueDate}.thenByDescending { it.priority})
    }
}