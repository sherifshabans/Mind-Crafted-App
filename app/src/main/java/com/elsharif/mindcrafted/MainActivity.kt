package com.elsharif.mindcrafted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.elsharif.mindcrafted.presentation.dashboard.DashboardScreen
import com.elsharif.mindcrafted.presentation.theme.MindCraftedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindCraftedTheme {
                DashboardScreen()
            }
        }
    }
}
