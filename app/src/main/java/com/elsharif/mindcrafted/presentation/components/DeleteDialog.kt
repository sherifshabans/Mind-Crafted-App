package com.elsharif.mindcrafted.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialog(
    isOpen:Boolean,
    title:String,
    bodyText:String,
    onDismissRequest:()->Unit,
    onConfirmRequest:()->Unit

) {

    if(isOpen){
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {Text(text = title)},
            text = {Text(text = bodyText)
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {

                TextButton(
                    onClick =onConfirmRequest,
                ) {
                    Text(text = "Delete")
                }
            },
        )
    }



}