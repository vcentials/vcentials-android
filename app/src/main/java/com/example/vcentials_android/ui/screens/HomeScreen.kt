import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color

import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_temp") }) {
                Text("+")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Text("Home") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Text("Menu") },
                    selected = false,
                    onClick = { navController.navigate("menu") }
                )
            }
        }
    ) {
        Column {
            Text("Hello Testly", style = MaterialTheme.typography.headlineMedium)
            Text("Test Section", color = Color.Red)
            // Add a list or cards to display temperatures.
        }
    }
}
