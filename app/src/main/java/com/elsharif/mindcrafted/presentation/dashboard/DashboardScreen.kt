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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elsharif.mindcrafted.R
import com.elsharif.mindcrafted.domain.model.Session
import com.elsharif.mindcrafted.domain.model.Subject
import com.elsharif.mindcrafted.domain.model.Task
import com.elsharif.mindcrafted.presentation.components.CountCard
import com.elsharif.mindcrafted.presentation.components.SubjectCard
import com.elsharif.mindcrafted.presentation.components.studySessionsList
import com.elsharif.mindcrafted.presentation.components.tasksList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen() {

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
        ),Task(
            title = "Prepare exam2",
            description = "",
            dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 1,
            taskId = 0
        ),Task(
            title = "Prepare exam3",
            description = "",
            dueDate = 0L,
            priority = 3,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 1,
            taskId = 0
        ),Task(
            title = "Prepare exam4",
            description = "",
            dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isComplete = false,
            taskSubjectId = 1,
            taskId = 0
        ),Task(
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

    Scaffold(
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
                    subjectCount =5,
                    studiedHours ="10",
                    goalHours ="18"
                )
            }
            item {
                SubjectCardsSection(
                    modifier =Modifier.fillMaxWidth(),
                    subjectList = subject
                )
            }

            item {
                Button(
                    onClick = { /*TODO*/ },
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
                onDeleteItemClick = {}
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
    emptyListText:String="You don't have any subject.\n Click the + button to add new subject."
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
            IconButton(onClick = { /*TODO*/ }) {
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
                    gradientColors =subject.colors,
                    onClick = {}
                )

            }

        }
    }

}