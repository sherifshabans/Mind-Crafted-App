package com.elsharif.mindcrafted.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.presentation.components.AddSubjectDialog
import com.elsharif.mindcrafted.presentation.components.CountCard
import com.elsharif.mindcrafted.presentation.components.DeleteDialog
import com.elsharif.mindcrafted.presentation.components.studySessionsList
import com.elsharif.mindcrafted.presentation.components.tasksList
import com.elsharif.mindcrafted.sessions
import com.elsharif.mindcrafted.tasks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen() {

    val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFABExpended by remember {
        derivedStateOf { listState.firstVisibleItemIndex==0  }
    }
    var isAddSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDeleteSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isEditSubjectDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var subjectName by remember {
        mutableStateOf("")
    }
    var goalHours by remember {
        mutableStateOf("")
    }
    var selectedColor by remember {
        mutableStateOf(Subject.subjectCardColors.random())
    }
    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen=false},
        onConfirmRequest = {
            isAddSubjectDialogOpen=true
        },
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange ={subjectName=it} ,
        onGoalHoursChange = {goalHours=it},
        selectedColor = selectedColor,
        onColorChange = {selectedColor=it},

        )

    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title ="Delete Subject",
        bodyText ="Are you sure , you want to delete this subject? All related" +
                "tasks and study sessions will be permanently removed. this action can not be undone",
        onDismissRequest = { isAddSubjectDialogOpen=false },
        onConfirmRequest = { isAddSubjectDialogOpen=false },
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                title = "Math",
                onBackButtonClick = { /*TODO*/ },
                onDeleteButtonClick = { isDeleteSubjectDialogOpen=true },
                onEditButtonClick = {isEditSubjectDialogOpen=true},
                scrollBehavior = scrollBehavior

            )

        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add")},
                text = { Text(text = "Add Task")},
                expanded = isFABExpended
                )
        }
    ) {paddingValues->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                SubjectOverview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHours ="10",
                    goalHours ="5",
                    progress =.75f
                )
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks. \n " +
                        "Click the + button  to add new task.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            tasksList(
                sectionTitle = "Completed TASKS",
                emptyListText = "You don't have any complete tasks. \n " +
                        "Click the check box on completion of tasks.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent sessions. \n " +
                        "Start a study session to begin recording your progress .",
                sessions = sessions,
                onDeleteItemClick = {isDeleteSubjectDialogOpen=true}
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopBar(
    title:String,
    onBackButtonClick:()->Unit,
    onDeleteButtonClick:()->Unit,
    onEditButtonClick:()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior =scrollBehavior ,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="navigate back" )

            }
        },
        title = { Text(
            text = "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineSmall
        ) },
        actions = {
            IconButton(onClick = { onDeleteButtonClick }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )

            }
            IconButton(onClick = { onEditButtonClick }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )

            }

        }
    )

}

@Composable
private fun SubjectOverview(
    modifier: Modifier,
    studiedHours:String,
    goalHours:String,
    progress:Float
) {
    val percentageProgress = remember(progress) {
        (progress*100).toInt().coerceIn(0,100)
    }
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        CountCard(
            modifier = Modifier.weight(1f),
            headingText ="Goal Study Hours",
            count =goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText ="Study Hours",
            count =studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Text(text = "$percentageProgress%")

        }
    }

}