import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color

@Composable
fun MenuScreen(navController: NavHostController) {
    Column {
        Text("Hello Testly")
        Button(onClick = { /* Handle logout */ }) {
            Text("Logout")
        }
    }
}
