import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var temperatureList by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var userName by remember { mutableStateOf("User") }

    // Retrieve the user's name and temperature data from Firestore
    LaunchedEffect(Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userName = document.getString("name") ?: "User"
                    }
                }
                .addOnFailureListener {
                    userName = "User"
                }

            db.collection("temperatures")
                .get()
                .addOnSuccessListener { result ->
                    temperatureList = result.documents.mapNotNull { it.data }
                }
                .addOnFailureListener {
                    // Handle error
                }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_temp") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Temperature")
            }
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                        selected = true,
                        onClick = { /* Navigate to Home */ }
                    )
                    NavigationBarItem(
                        icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu") },
                        selected = false,
                        onClick = { navController.navigate("menu") }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Display the user's name at the top
            Text(
                text = "Hello $userName",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Section header
            Text(
                text = "Temperature Records",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display temperature list from Firestore
            temperatureList.forEach { data ->
                val date = data["date"]?.toString() ?: ""
                val location = data["location"]?.toString() ?: ""
                val machine = data["machine"]?.toString() ?: ""
                val machineTemp = data["machineTemp"]?.toString() ?: ""
                val room = data["room"]?.toString() ?: ""
                val roomTemp = data["roomTemp"]?.toString() ?: ""
                val time = data["time"]?.toString() ?: ""
                val username = data["username"]?.toString() ?: ""

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Column {
                        Text(
                            text = "Date: $date",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Location: $location",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Machine: $machine",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Machine Temperature: $machineTemp",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Room: $room",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Room Temperature: $roomTemp",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Time: $time",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Username: $username",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
