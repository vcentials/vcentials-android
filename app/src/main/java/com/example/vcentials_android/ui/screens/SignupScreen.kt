import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SignupScreen(navController: NavHostController) {
    Surface(color = Color.Red, modifier = Modifier.fillMaxSize()) {
        Column {
            TextField(value = "", onValueChange = {}, placeholder = { Text("Name") })
            TextField(value = "", onValueChange = {}, placeholder = { Text("Email") })
            TextField(value = "", onValueChange = {}, placeholder = { Text("Password") })
            TextField(value = "", onValueChange = {}, placeholder = { Text("Repeat Password") })
            Button(onClick = { navController.navigate("home") }) {
                Text("Login")
            }
        }
    }
}
