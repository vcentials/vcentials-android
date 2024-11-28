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
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.vcentials_android.navigation.UserSession
import com.example.vcentials_android.ui.theme.ValenciaRed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun HomeScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    var temperatureList by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var isLoading by remember { mutableStateOf(false) }

    // Function to fetch data from Firestore
    fun fetchTemperatureData() {
        isLoading = true
        db.collection("temperatures")
            .orderBy("date", Query.Direction.DESCENDING) // Sort in descending order
            .limit(3) // most recent 3 entries
            .get()
            .addOnSuccessListener { result ->
                temperatureList = result.documents.mapNotNull { it.data }
                isLoading = false
            }
            .addOnFailureListener { exception ->
                // Handle error
                isLoading = false
            }
    }

    // Fetch the temperature data initially
    LaunchedEffect(Unit) {
        fetchTemperatureData()
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
                text = "Hello ${UserSession.userName}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Section header
            Text(
                text = "Temperature Records",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = ValenciaRed,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = { fetchTemperatureData() },
                modifier = Modifier.padding(bottom = 16.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Refresh Records")
            }

            // Display temperature list from Firestore
            if (isLoading) {
                // Show a loading indicator while fetching data
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                temperatureList.forEach { data ->
                    val date = data["date"]?.toString() ?: ""
                    val time = data["time"]?.toString() ?: ""
                    val location = data["location"]?.toString() ?: ""
                    val machine = data["machine"]?.toString() ?: ""
                    val machineTemp = data["machineTemp"]?.toString() ?: ""
                    val room = data["room"]?.toString() ?: ""
                    val roomTemp = data["roomTemp"]?.toString() ?: ""
                    val username = data["username"]?.toString() ?: ""

                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Time and Date at the top right
                        Text(
                            text = "$time, $date",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                                .padding(bottom = 16.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Location: $location",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Machine: $machine",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = "Machine Temperature: $machineTemp",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Room: $room",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = "Room Temperature: $roomTemp",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "Username: $username",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
