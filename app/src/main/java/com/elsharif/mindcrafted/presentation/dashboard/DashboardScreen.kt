package com.elsharif.mindcrafted.presentation.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elsharif.mindcrafted.R
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.presentation.components.AddSubjectDialog
import com.elsharif.mindcrafted.presentation.components.CountCard
import com.elsharif.mindcrafted.presentation.components.DeleteDialog
import com.elsharif.mindcrafted.presentation.components.SubjectCard
import com.elsharif.mindcrafted.presentation.components.studySessionsList
import com.elsharif.mindcrafted.presentation.components.tasksList
import com.elsharif.mindcrafted.presentation.destinations.SessionScreenRouteDestination
import com.elsharif.mindcrafted.presentation.destinations.SubjectScreenRouteDestination
import com.elsharif.mindcrafted.presentation.destinations.TaskScreenRouteDestination
import com.elsharif.mindcrafted.presentation.subject.SubjectScreenNavArgs
import com.elsharif.mindcrafted.presentation.task.TaskScreenNavArgs
import com.elsharif.mindcrafted.sessions
import com.elsharif.mindcrafted.subject
import com.elsharif.mindcrafted.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elsharif.mindcrafted.util.SnackbarEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Destination(start = true)
@Composable
fun DashboardScreenRoute(
    navigator :DestinationsNavigator
) {

    val viewModel:DashboardViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    val recentSessions by viewModel.recentSessions.collectAsStateWithLifecycle()


    DashboardScreen(
        state = state,
        tasks = tasks,
        recentSessions = recentSessions,
        onEvent = viewModel::onEvent,
        snackbarEvent = viewModel.snackbarEventFlow,
        onSubjectCardClick = { subjectId->
            subjectId?.let {
                val navArg = SubjectScreenNavArgs(subjectId=subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArg))
            }

        },
        onTaskCardClick = { taskId->
            taskId?.let {
                val navArg = TaskScreenNavArgs(taskId=taskId,subjectId = null)
                navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
            }

        },
        onStartSessionButtonClick ={
            navigator.navigate(SessionScreenRouteDestination())

        }
        )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DashboardScreen(
    state: DashboardState,
    tasks:List<Task>,
    recentSessions:List<Session>,
    onEvent: (DashboardEvent)->Unit,
    snackbarEvent: SharedFlow<SnackbarEvent> ,
    onSubjectCardClick: (Int?) ->Unit,
    onTaskCardClick: (Int?)->Unit,
    onStartSessionButtonClick: ()->Unit
) {


    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event->
            when(event){
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                        )
                }
            }
        }
    }

    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        selectedColor = state.subjectCardColors,
        onSubjectNameChange ={onEvent(DashboardEvent.onSubjectNameChange(it))} ,
        onGoalHoursChange = {onEvent(DashboardEvent.onGoalStudyHoursChange(it))},
        onColorChange = {onEvent(DashboardEvent.onSubjectCardColorChange(it))},
        onDismissRequest = { isAddSubjectDialogOpen=false},
        onConfirmRequest = {
            onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialogOpen=false },

    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title ="Delete Session?",
        bodyText ="Are you sure , you want to delete this session? Your studied hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isAddSubjectDialogOpen=false },
        onConfirmRequest = {
          onEvent(DashboardEvent.DeleteSession)
            isAddSubjectDialogOpen=false

                           },
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {DashboardScreenTopBar()}
    ) {paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount =state.totalSubjectCount,
                    studiedHours =state.totalStudiedHours.toString(),
                    goalHours =state.totalGoalStudyHours.toString()
                )
            }
            item {
                SubjectCardsSection(
                    modifier =Modifier.fillMaxWidth(),
                    subjectList = state.subjects,
                    onAddIconClick = { isAddSubjectDialogOpen =true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }

            item {
                Button(
                    onClick = onStartSessionButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 48.dp)
                    ) {
                    Text(text = "Start Study Session")
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks. \n " +
                        "Click the + button in the subject screen to add new task.",
                tasks = tasks,
                onCheckBoxClick = {onEvent(DashboardEvent.onTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )

            item { 
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent sessions. \n " +
            "Start a study session to begin recording your progress .",
                sessions = recentSessions,
                onDeleteItemClick = {
                    onEvent(DashboardEvent.onDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen=true}
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar () {

    CenterAlignedTopAppBar(
        title = {
            Text(text = "Mind Crafted", style = MaterialTheme.typography.headlineMedium)
        }
    )

}


@Composable
private fun CountCardSection(
    modifier: Modifier,
    subjectCount:Int,
    studiedHours:String,
    goalHours:String
){
    Row (
        modifier =modifier
    ){
        CountCard(
            modifier = Modifier.weight(1f),
            headingText ="Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText ="Goal Study Hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText ="Studied Hours",
            count = studiedHours
        )

    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText:String="You don't have any subject.\n Click the + button to add new subject.",
    onAddIconClick:()->Unit,
    onSubjectCardClick:(Int?)->Unit
){
    Column(
        modifier=modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "SUBJECT",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClick ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription ="Add Subject"
                )
                
            }

        }
        if(subjectList.isEmpty()){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_books),
                contentDescription =emptyListText
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text =emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow(

            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList){subject->
                SubjectCard(
                    subjectName =subject.name,
                    gradientColors =subject.colors.map { Color(it)},
                    onClick = {onSubjectCardClick(subject.subjectId)}
                )

            }

        }
    }

}