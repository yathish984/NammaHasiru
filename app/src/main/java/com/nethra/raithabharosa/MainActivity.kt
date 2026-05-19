package com.nethra.raithabharosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}

data class FarmerHistory(
    val farmer: String = "",
    val crop: String = "",
    val village: String = "",
    val moisture: String = "",
    val temperature: String = "",
    val recommendation: String = ""
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var currentFarmer by remember { mutableStateOf("") }
    var currentVillage by remember { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("home") {
            HomeScreen(navController) { farmer, village ->
                currentFarmer = farmer
                currentVillage = village
            }
        }

        composable("history") {
            HistoryScreen()
        }

        composable("profile") {
            ProfileScreen(currentFarmer, currentVillage)
        }

        composable("about") {
            AboutScreen()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate("home")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.farmerlogo),
                contentDescription = null,
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Raitha Bharosa Hub",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "AI Powered Smart Farming Assistant",
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onProfileUpdate: (String, String) -> Unit
) {

    val db = FirebaseFirestore.getInstance()

    var farmerName by remember { mutableStateOf("") }
    var cropName by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var moisture by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }

    var sowingIndex by remember { mutableStateOf(0) }
    var recommendation by remember { mutableStateOf("") }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.leaflogo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(55.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {

                            Text(
                                text = "Raitha Bharosa Hub",
                                color = Color.White,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "Smart Agriculture Platform",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20)
                )
            )
        },

        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("history")
                    },
                    icon = { Icon(Icons.Default.History, null) },
                    label = { Text("History") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("profile")
                    },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("about")
                    },
                    icon = { Icon(Icons.Default.Info, null) },
                    label = { Text("About") }
                )
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F7F2))
                .padding(16.dp)
        ) {

            item {

                Card(
                    shape = RoundedCornerShape(22.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "Farmer Registration",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        OutlinedTextField(
                            value = farmerName,
                            onValueChange = { farmerName = it },
                            label = { Text("Farmer Name") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = cropName,
                            onValueChange = { cropName = it },
                            label = { Text("Crop Name") },
                            leadingIcon = {
                                Icon(Icons.Default.Grass, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = village,
                            onValueChange = { village = it },
                            label = { Text("Village") },
                            leadingIcon = {
                                Icon(Icons.Default.LocationOn, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = mobile,
                            onValueChange = { mobile = it },
                            label = { Text("Mobile Number") },
                            leadingIcon = {
                                Icon(Icons.Default.Phone, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Soil & Weather Analysis",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color(0xFF1B5E20)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = moisture,
                            onValueChange = { moisture = it },
                            label = { Text("Moisture %") },
                            leadingIcon = {
                                Icon(Icons.Default.WaterDrop, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = temperature,
                            onValueChange = { temperature = it },
                            label = { Text("Temperature °C") },
                            leadingIcon = {
                                Icon(Icons.Default.Thermostat, null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        Button(
                            onClick = {

                                val moistureValue =
                                    moisture.trim().toIntOrNull() ?: 0

                                val temperatureValue =
                                    temperature.trim().toIntOrNull() ?: 0

                                sowingIndex =
                                    when {
                                        moistureValue >= 70 &&
                                                temperatureValue in 20..30 -> 92

                                        moistureValue >= 60 &&
                                                temperatureValue in 25..35 -> 75

                                        moistureValue >= 40 -> 58

                                        else -> 32
                                    }

                                recommendation =
                                    when {
                                        sowingIndex >= 85 ->
                                            "Excellent Conditions - Sow Today"

                                        sowingIndex >= 65 ->
                                            "Good Conditions - Suitable for Farming"

                                        sowingIndex >= 45 ->
                                            "Wait for Better Conditions"

                                        else ->
                                            "Poor Conditions - Apply Fertilizer & Irrigation"
                                    }

                                onProfileUpdate(
                                    farmerName,
                                    village
                                )

                                val history = FarmerHistory(
                                    farmerName,
                                    cropName,
                                    village,
                                    moisture,
                                    temperature,
                                    recommendation
                                )

                                db.collection("history")
                                    .add(history)
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1B5E20)
                            )
                        ) {

                            Text(
                                text = "Generate AI Recommendation",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        if (recommendation.isNotEmpty()) {

                            Text(
                                text = "Recommendation Dashboard",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Card(
                                shape = RoundedCornerShape(20.dp)
                            ) {

                                Column(
                                    modifier = Modifier.padding(18.dp)
                                ) {

                                    Text(
                                        text = "Sowing Index",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "$sowingIndex%",
                                            fontSize = 44.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF2E7D32)
                                        )

                                        CircularProgressIndicator(
                                            progress = { sowingIndex / 100f },
                                            modifier = Modifier.size(120.dp),
                                            strokeWidth = 10.dp,
                                            color = Color(0xFF43A047)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                shape = RoundedCornerShape(20.dp)
                            ) {

                                Column(
                                    modifier = Modifier.padding(18.dp)
                                ) {

                                    Text(
                                        text = "Recommendation",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Text(
                                        text = recommendation,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFF57C00)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen() {

    val db = FirebaseFirestore.getInstance()

    val historyList = remember {
        mutableStateListOf<FarmerHistory>()
    }

    LaunchedEffect(Unit) {

        db.collection("history")
            .get()
            .addOnSuccessListener { result ->

                historyList.clear()

                for (document in result) {

                    val item =
                        document.toObject(FarmerHistory::class.java)

                    historyList.add(item)
                }
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(historyList) { item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = item.farmer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Crop: ${item.crop}")
                    Text("Village: ${item.village}")
                    Text("Moisture: ${item.moisture}%")
                    Text("Temperature: ${item.temperature}°C")

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = item.recommendation,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(name: String, village: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7F2))
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.farmerlogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(25.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = if (name.isNotEmpty()) name else "Farmer",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = village,
            fontSize = 20.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun AboutScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.leaflogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Raitha Bharosa Hub",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "AI Powered Smart Agriculture Platform",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text =
                "Raitha Bharosa Hub is an intelligent smart farming platform developed using Android Studio, Kotlin, Firebase Firestore and Jetpack Compose. The system helps farmers make better agricultural decisions using AI based recommendation logic, soil analysis and environmental monitoring.",
            fontSize = 16.sp
        )
    }
}