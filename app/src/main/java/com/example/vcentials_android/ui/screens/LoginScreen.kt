import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.example.vcentials_android.navigation.UserSession
import com.example.vcentials_android.ui.theme.TextGrey
import com.example.vcentials_android.ui.theme.ValenciaRed
import com.google.firebase.firestore.getField
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun LoginScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Surface(
        color = ValenciaRed, // Setting the background to match the provided red color
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Sign In Text Field
            Text("Sign In", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                isError = emailError != null
            )
            emailError?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                isError = passwordError != null,
                visualTransformation = PasswordVisualTransformation() // Hides the password input
            )
            passwordError?.let {
                Text(
                    text = it,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Sign In Button
            Button(
                onClick = {
                    emailError = null
                    passwordError = null
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        db.collection("users")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener { result ->
                                if (result.isEmpty) {
                                    emailError = "Unknown email"
                                } else {
                                    val user = result.documents[0]
                                    val storedPassword = user.getString("password")
                                    if (storedPassword == password) {
                                        val userId = user.id
                                        val userName = user.getString("name")

                                        UserSession.userId = userId  // Set global userId

                                        if (userName != null) {
                                            UserSession.userName = userName // Set global userName
                                        }
                                        navController.navigate("home")
                                    } else {
                                        passwordError = "Invalid password"
                                    }
                                }
                            }
                            .addOnFailureListener {
                                emailError = "Login failed: ${it.message}"
                            }
                    } else {
                        if (email.isEmpty()) emailError = "Email cannot be empty"
                        if (password.isEmpty()) passwordError = "Password cannot be empty"
                    }
                },
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
                color = Color.White,
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
                        color = Color.White
                    )
                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        modifier = Modifier
                            .clickable { navController.navigate("signup") },
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
                    )
                }
            }
        }
    }
}