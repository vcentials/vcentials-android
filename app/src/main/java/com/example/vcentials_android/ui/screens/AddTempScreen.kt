import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.vcentials_android.navigation.UserSession

@Composable
fun AddTempScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()

    // Get current date and time
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    // State to remember all input fields
    var location by remember { mutableStateOf("") }
    var machine by remember { mutableStateOf("") }
    var machineTemp by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var roomTemp by remember { mutableStateOf("") }
    var username by remember { mutableStateOf(UserSession.userName ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // top row with "X" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Text(
                text = "Add Temperature Record",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        }

        // Input fields
        TextField(
            value = location,
            onValueChange = { location = it },
            placeholder = { Text("Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = machine,
            onValueChange = { machine = it },
            placeholder = { Text("Machine") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = machineTemp,
            onValueChange = { machineTemp = it },
            placeholder = { Text("Machine Temperature") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = room,
            onValueChange = { room = it },
            placeholder = { Text("Room") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = roomTemp,
            onValueChange = { roomTemp = it },
            placeholder = { Text("Room Temperature") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Error Message
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Submit Button
        Button(
            onClick = {
                if (location.isNotEmpty() && machine.isNotEmpty() && machineTemp.isNotEmpty() &&
                    room.isNotEmpty() && roomTemp.isNotEmpty() && username.isNotEmpty()
                ) {
                    // Adding temperature data to Firestore
                    val temperatureData = hashMapOf(
                        "date" to currentDate,
                        "location" to location,
                        "machine" to machine,
                        "machineTemp" to machineTemp,
                        "room" to room,
                        "roomTemp" to roomTemp,
                        "time" to currentTime,
                        "username" to username
                    )
                    db.collection("temperatures")
                        .add(temperatureData)
                        .addOnSuccessListener {
                            // Navigate to home screen on success
                            navController.navigate("home")
                        }
                        .addOnFailureListener { e ->
                            // Show error message on failure
                            errorMessage = "Failed to save data: ${e.message}"
                        }
                } else {
                    errorMessage = "All fields must be filled out"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Submit", color = Color.White)
        }
    }
}
