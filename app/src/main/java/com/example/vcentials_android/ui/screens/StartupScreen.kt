import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vcentials_android.ui.theme.ValenciaRed

@Composable
fun StartupScreen(navController: NavHostController) {
    Surface(
        color = ValenciaRed, // Setting the background to match the provided red color
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            Text(
                text = "TEMP CHECK",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )

            // Login Button
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Login", color = Color.White)
            }

            // "Or" Text
            Text(
                text = "Or",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )

            // Signup Button
            Button(
                onClick = { navController.navigate("signup") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Signup", color = Color.White)
            }
        }
    }
}

