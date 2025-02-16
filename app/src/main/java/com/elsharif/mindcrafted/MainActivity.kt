package com.elsharif.mindcrafted

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.presentation.NavGraph
import com.elsharif.mindcrafted.presentation.NavGraphs
import com.elsharif.mindcrafted.presentation.destinations.SessionScreenRouteDestination
import com.elsharif.mindcrafted.presentation.session.StudySessionTimerServices
import com.elsharif.mindcrafted.presentation.theme.MindCraftedTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerServices
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StudySessionTimerServices.StudySessionTimerBinder
            timerService =binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this,StudySessionTimerServices::class.java).also {intent->
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(isBound){
        setContent {
            MindCraftedTheme {

                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(SessionScreenRouteDestination) { timerService }
                    }
                )
            }
        }
        }
        requestPermission()
    }
    private fun requestPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound =false

    }
}

val subject= listOf(
    Subject(name = "Math", goalHours = 8f, colors = Subject.subjectCardColors[0].map { it.toArgb() }, subjectId = 0),
    Subject(name = "Math2", goalHours = 4f, colors = Subject.subjectCardColors[1].map { it.toArgb() }, subjectId = 0),
    Subject(name = "Math3", goalHours = 2f, colors = Subject.subjectCardColors[2].map { it.toArgb() }, subjectId = 0),
    Subject(name = "Math4", goalHours = 3f, colors = Subject.subjectCardColors[3].map { it.toArgb() }, subjectId = 1),
    Subject(name = "Math5", goalHours = 1f, colors = Subject.subjectCardColors[4].map { it.toArgb() }, subjectId = 2),
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

