package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikko1.navigation.ROUTE_CALENDAR
import com.example.viikko1.navigation.ROUTE_HOME
import com.example.viikko1.ui.theme.Viikko1Theme
import com.example.viikko1.view.CalendarScreen
import com.example.viikko1.view.HomeScreen
import com.example.viikko1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Viikko1Theme() {
                val navController = rememberNavController()
                val viewModel: TaskViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_HOME
                ) {
                    composable(ROUTE_HOME) {
                        HomeScreen(
                            viewModel = viewModel,
                            onTaskClick = { id -> viewModel.openTask(id) },
                            onAddClick = { viewModel.addTaskDialogVisible.value = true},
                            onNavigateCalendar = { navController.navigate(ROUTE_CALENDAR) }
                        )
                    }
                    composable(ROUTE_CALENDAR) {
                        CalendarScreen(
                            viewModel = viewModel,
                            onTaskClick = { id -> viewModel.openTask(id) },
                            onNavigateHome = { navController.navigate(ROUTE_HOME) }
                        )
                    }
                }
            }
        }
    }
}

