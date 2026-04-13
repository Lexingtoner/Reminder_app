package com.example.reminder_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminder_app.ui.theme.Reminder_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Reminder_appTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "list") {
                    composable("list") { ListScreen(navController) }
                    composable("form") { FormScreen(navController) }
                }
            }
        }
    }
}

@Composable
fun ListScreen(navController: NavController) {
    Scaffold(
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(onClick = { navController.navigate("form") }) {
                androidx.compose.material3.Text("+")
            }
        }
    ) { innerPadding ->
        List(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun FormScreen(navController: NavController) {
    Form(onReminderAdded = { navController.popBackStack() })
}