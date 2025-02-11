package com.elsharif.mindcrafted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.presentation.NavGraph
import com.elsharif.mindcrafted.presentation.NavGraphs
import com.elsharif.mindcrafted.presentation.theme.MindCraftedTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindCraftedTheme {

                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}

val subject= listOf(
    Subject(name = "Math", goalHours = 8f, colors = Subject.subjectCardColors[0], subjectId = 0),
    Subject(name = "Math2", goalHours = 4f, colors = Subject.subjectCardColors[1], subjectId = 0),
    Subject(name = "Math3", goalHours = 2f, colors = Subject.subjectCardColors[2], subjectId = 0),
    Subject(name = "Math4", goalHours = 3f, colors = Subject.subjectCardColors[3], subjectId = 1),
    Subject(name = "Math5", goalHours = 1f, colors = Subject.subjectCardColors[4], subjectId = 2),
)
val tasks = listOf(
    Task(
        title = "Prepare exam",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 1,
        taskId = 0
    ),
    Task(
        title = "Prepare exam2",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 1,
        taskId = 0
    ),
    Task(
        title = "Prepare exam3",
        description = "",
        dueDate = 0L,
        priority = 3,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 1,
        taskId = 0
    ),
    Task(
        title = "Prepare exam4",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 1,
        taskId = 0
    ),
    Task(
        title = "Prepare exam5",
        description = "",
        dueDate = 0L,
        priority = 3,
        relatedToSubject = "",
        isComplete = true,
        taskSubjectId = 1,
        taskId = 0
    ),
)
val sessions= listOf(
    Session(
        relatedToSubject = "Math",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        sessionId = 0
    ), Session(
        relatedToSubject = "Math2",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        sessionId = 0
    ), Session(
        relatedToSubject = "Math3",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        sessionId = 0
    ), Session(
        relatedToSubject = "Math4",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        sessionId = 0
    ), Session(
        relatedToSubject = "Math5",
        date = 0L,
        duration = 2L,
        sessionSubjectId = 0,
        sessionId = 0
    ),
)

