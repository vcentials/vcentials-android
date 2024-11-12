import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignupScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()

    // Remember the states for the input fields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(color = Color(0xFFB71C1C), modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Name TextField
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )

            // Repeat Password TextField
            TextField(
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                placeholder = { Text("Repeat Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Error Message
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Sign Up Button
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && password == repeatPassword) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Navigate to home screen on successful signup
                                    navController.navigate("home")
                                } else {
                                    // Set error message to display
                                    errorMessage = task.exception?.message ?: "Signup failed"
                                }
                            }
                    } else {
                        errorMessage = if (email.isEmpty() || password.isEmpty()) {
                            "Email and Password cannot be empty"
                        } else {
                            "Passwords do not match"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Sign Up", color = Color.White)
            }
        }
    }
}
