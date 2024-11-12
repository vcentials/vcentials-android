import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.vcentials_android.navigation.UserSession
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MenuScreen(navController: NavHostController) {

    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                        selected = false,
                        onClick = { navController.navigate("home") }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu") },
                        selected = true,
                        onClick = { /* Stay on Menu Screen */ }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Hello, ${UserSession.userName}")
            Button(
                onClick = {
                    // Handle logout logic
                    UserSession.userId = null.toString()
                    UserSession.userName = null.toString()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true } // Clear the back stack to force re-login
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}