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
import com.example.vcentials_android.navigation.UserSession
import com.google.firebase.firestore.FirebaseFirestore
import com.example.vcentials_android.ui.theme.*

@Composable
fun SignupScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(color = ValenciaRed, modifier = Modifier.fillMaxSize()) {
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
                    .padding(bottom = 8.dp),
                isError = errorMessage == "Email already exists"
            )
            errorMessage?.takeIf { it == "Email already exists" }?.let {
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

            // Error Message for passwords
            errorMessage?.takeIf { it != "Email already exists" }?.let {
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
                        db.collection("users")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener { result ->
                                if (result.isEmpty) {
                                    val newUser = hashMapOf(
                                        "name" to name,
                                        "email" to email,
                                        "password" to password
                                    )

                                    db.collection("users")
                                        .add(newUser)
                                        .addOnSuccessListener { documentReference ->
                                            val userId = documentReference.id
                                            UserSession.userId = userId  // Set global userId
                                            UserSession.userName = name // Set global userName
                                            navController.navigate("home")
                                        }
                                        .addOnFailureListener {
                                            errorMessage = "Signup failed: ${it.message}"
                                        }
                                } else {
                                    errorMessage = "Email already exists"
                                }
                            }
                            .addOnFailureListener {
                                errorMessage = "Signup failed: ${it.message}"
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
