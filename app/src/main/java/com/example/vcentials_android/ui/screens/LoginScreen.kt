import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vcentials_android.ui.theme.TextGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    // Remembering the state of the email and password text fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = Color(0xFFB71C1C), // Setting the background to match the provided red color
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Sign In Text Field
            Text("Sign In", style = MaterialTheme.typography.headlineLarge, color = TextGrey)
            Spacer(modifier = Modifier.height(16.dp))

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                )


            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                singleLine = true,
                )


            // Sign In Button
            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Sign in", color = Color.White)
            }

            // Forgot Password
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Password?",
                color = TextGrey,
                modifier = Modifier
                    .clickable { /* TODO Password Recovery Action */ }
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
            )

            // Sign up for users that don't have a login
            Spacer(modifier = Modifier.height(120.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Don't have an account? ",
                        color = TextGrey
                    )
                    Text(
                        text = "Sign Up",
                        color = TextGrey,
                        modifier = Modifier
                            .clickable { /* TODO Sign Up Action */ },
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
                    )
                }
            }
        }
    }
}