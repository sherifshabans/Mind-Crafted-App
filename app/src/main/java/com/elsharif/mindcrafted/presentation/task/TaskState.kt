package com.elsharif.mindcrafted.presentation.task

import android.app.ActivityManager.TaskDescription
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.util.Priority

data class TaskState(
    val title:String="",
    val description: String="",
    val dueDate:Long?=null,
    val isTaskComplete:Boolean =false,
    val priority: Priority =Priority.LOW,
    val relatedToSubject:String?=null,
    val subject: List<Subject> = emptyList(),
    val subjectId: Int?=null,
    val currentTaskId:Int?=null
)
