package com.elsharif.mindcrafted.presentation.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elsharif.mindcrafted.presentation.components.DeleteDialog
import com.elsharif.mindcrafted.presentation.components.SubjectListBottomSheet
import com.elsharif.mindcrafted.presentation.components.studySessionsList
import com.elsharif.mindcrafted.sessions
import com.elsharif.mindcrafted.subject
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen() {

    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subject,
        onDismissRequest = {isBottomSheetOpen =false},
        onSubjectClicked = {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if(!sheetState.isVisible)isBottomSheetOpen = false
            }

        }
    )
    DeleteDialog(
        isOpen =isDeleteDialogOpen,
        title = "Delete Session",
        bodyText ="Are you sure , you want to delete this session? " +
                "This action can not be undone. ",
        onDismissRequest = {isDeleteDialogOpen=false},
        onConfirmRequest = {
            isDeleteDialogOpen=false
        }
    )


    Scaffold(
        topBar = { SessionScreenTopBar(onBackButtonClick = {}) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {

            item{
                TimeSection(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubjectSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                    relatedToSubject ="English",
                    selectSubjectButtonClick = {isBottomSheetOpen =true}
                    )
            }
            item {
                ButtonsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    startButtonClick = {},
                    cancelButtonClick = {},
                    finishButtonClick = {}
                )
            }

            studySessionsList(
                sectionTitle = "STUDY SESSIONS HISTORY",
                emptyListText = "You don't have any recent sessions. \n " +
                        "Start a study session to begin recording your progress .",
                sessions = sessions,
                onDeleteItemClick = {isDeleteDialogOpen =true}
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreenTopBar(
    onBackButtonClick :()->Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate to Back Screen"
                )
            }
        },
        title = { Text(text = "Study Session", style = MaterialTheme.typography.headlineSmall) },
    )

}

@Composable
private fun TimeSection(
    modifier: Modifier
) {
    Box(
        modifier=modifier,
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .size(250.dp)
            .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )

        Text(text = "00:05:45", style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp))

    }

}


@Composable
private fun RelatedToSubjectSection(
    modifier: Modifier,
    relatedToSubject:String,
    selectSubjectButtonClick:()->Unit

) {

    Column(modifier = modifier) {
        Text(
            text = "Related to subject",
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Math",
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = selectSubjectButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Subject"
                )

            }

        }
    }
}

@Composable
private fun ButtonsSection(
    modifier: Modifier,
    startButtonClick:()->Unit,
    cancelButtonClick:()->Unit,
    finishButtonClick:()->Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Button(onClick = cancelButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text ="Cancel")
        }
        Button(onClick = startButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text ="Start")
        }
        Button(onClick = finishButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text ="Finish")
        }
    }
    
}