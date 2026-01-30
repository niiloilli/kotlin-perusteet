package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.ui.theme.Viikko1Theme
import com.example.viikko1.view.HomeScreen
import com.example.viikko1.viewmodel.TaskViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Viikko1Theme {
                val taskViewModel: TaskViewModel = viewModel()
                HomeScreen(viewModel = taskViewModel)
            }
        }
    }
}

