package com.elsharif.mindcrafted.presentation.subject

import androidx.compose.ui.graphics.Color
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task

data class SubjectState(
    val currentSubjectId:Int?= null,
    val subjectName:String ="",
    val goalStudyHours:String ="",
    val studiedHours: Float =0f,
    val subjectCardColors: List<Color> = Subject.subjectCardColors.random(),
    val recentSessions: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Session?=null,
    val progress:Float =0f,
)
