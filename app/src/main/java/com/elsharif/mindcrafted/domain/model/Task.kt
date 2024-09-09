package com.elsharif.mindcrafted.domain.model

import android.app.ActivityManager.TaskDescription

data class Task(
    val title:String,
    val description: String,
    val dueDate:Long,
    val priority:Int,
    val relatedToSubject:String,
    val isComplete:Boolean,
    val taskSubjectId:Int,
    val taskId:Int,

)
