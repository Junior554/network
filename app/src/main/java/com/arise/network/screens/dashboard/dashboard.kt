package com.arise.network.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.arise.network.R
import com.arise.network.navigation.NavigationGraph
import com.arise.network.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.floor

data class UserUsageStats(
    val currentDataUsageGB: Int,
    val totalDataAllowanceGB: Int,
    val planName: String,
    val renewalDate: String
)

data class Package(
    val id: String,
    val name: String,
    val price: String,
    val speed: String,
    val dataLimit: String,
    val features: List<String>,
    val ispName: String,
    val color: Color
)

data class ISP(
    val id: String,
    val name: String,
    val image: Int,
    val description: String,
    val rating: Double,
    val plansAvailable: Int
)

val mockUsageStats = UserUsageStats(
    currentDataUsageGB = 120,
    totalDataAllowanceGB = 500,
    planName = "Gold Plan",
    renewalDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .format(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 15) }.time)
)

val recommendedPackage = Package(
    id = "pkg-platinum",
    name = "Platinum Plus",
    price = "ksh 2500",
    speed = "5 Gbps",
    dataLimit = "Unlimited",
    features = listOf("Ultra-Fast Streaming", "Priority Support", "Free Modem"),
    ispName = "Safaricom",
    color = Color(0xFF7C3AED)
)

val featuredISPs = listOf(
    ISP("isp-001", "Safaricom", R.drawable.safaricom, "Lightning-fast fiber optic internet.", 4.8, 5),
    ISP("isp-002", "Airtel Network", R.drawable.airtel, "Affordable high-speed internet plans.", 4.5, 4),
    ISP("isp-003", "Telkom Network", R.drawable.telkom, "Reliable fiber services.", 4.3, 3),
)

@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBarContent(navController) },
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DataUsageBar(
                current = mockUsageStats.currentDataUsageGB,
                total = mockUsageStats.totalDataAllowanceGB
            )
            Spacer(modifier = Modifier.height(16.dp))

            UsageOverview(usageStats = mockUsageStats)
            Spacer(modifier = Modifier.height(8.dp))
            RecommendedPackageCard(navController, recommendedPackage)

            featuredISPs.forEach { isp ->
                FeaturedIspCard(isp)
            }
        }
    }
}

@Composable
fun DataUsageBar(current: Int, total: Int) {
    val usageFraction = current.toFloat() / total
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Data Usage", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            color = Color.LightGray
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth(fraction = usageFraction)
                    .height(20.dp)
            ) {}
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "$current GB of $total GB used")
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { navController.navigate(Routes.PACKAGE) }) {
                Text("Packages")
            }

            Button(onClick = {navController.navigate(Routes.LOG_OUT)
            }) {
                Text("Log Out")
            }
            Button(onClick = {navController.navigate(Routes.SERVICES)
            }) {
                Text("AddServices")
            }
            Button(onClick = {navController.navigate(Routes.MY_SERVICES)
            }) {
                Text("MYServices")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContent(navController: NavController) {
    TopAppBar(
        title = { Text("Royal internet") },
        actions = {
            IconButton(onClick = { navController.navigate(Routes.PROFILE) }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
            Text("Profile")
            IconButton(onClick = { navController.navigate(Routes.HELP) }) {
                Icon(Icons.Default.Person, contentDescription = "Help")
            }
            Text("Help Center")
            Spacer(modifier = Modifier.height(4.dp))

            Button(onClick = { navController.navigate(Routes.ABOUT_US) }) {
                Text("About Us")
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun UsageOverview(usageStats: UserUsageStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Plan: ${usageStats.planName}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Data Used: ${usageStats.currentDataUsageGB}GB / ${usageStats.totalDataAllowanceGB}GB")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Renewal Date: ${usageStats.renewalDate}")
        }
    }
}

@Composable
fun RecommendedPackageCard(navController: NavController, packageInfo: Package) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Recommended Package", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Surface(
                color = packageInfo.color,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(packageInfo.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(packageInfo.ispName, color = Color.White, fontSize = 14.sp)
                }
            }

            Text("${packageInfo.speed} Speed")
            Text("${packageInfo.dataLimit} Data")
            Text(
                text = "${packageInfo.price}/mo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = { navController.navigate(Routes.PACKAGE) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Details")
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
    Text("Some of the best internet providers in Kenya that we have partnerd with")
}

@Composable
fun FeaturedIspCard(isp: ISP) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(isp.image),
                contentDescription = "ISP Logo",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(isp.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(isp.description, color = Color.Gray, fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    repeat(floor(isp.rating).toInt()) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
