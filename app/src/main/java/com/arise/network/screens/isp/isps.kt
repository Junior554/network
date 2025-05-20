package com.arise.enetwork.ui.theme.screens.isps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import login

// --- DATA CLASS ---
data class ISP(
    val id: String,
    val name: String,
    val logoUrl: String,
    val description: String,
    val rating: Double,
    val plansAvailable: Int
)

// --- MOCK DATA ---
val mockIsps = listOf(
    ISP(
        id = "isp-001",
        name = "SpeedyNet",
        logoUrl = "https://picsum.photos/seed/speedynet/128/128",
        description = "Lightning-fast fiber optic internet for seamless connectivity and blazing speeds.",
        rating = 4.8,
        plansAvailable = 5
    ),
    ISP(
        id = "isp-002",
        name = "ConnectFast",
        logoUrl = "https://picsum.photos/seed/connectfast/128/128",
        description = "Reliable and affordable internet services for home and business users.",
        rating = 4.5,
        plansAvailable = 4
    ),
    ISP(
        id = "isp-003",
        name = "FiberOne",
        logoUrl = "https://picsum.photos/seed/fiberone/128/128",
        description = "Premium fiber internet with symmetrical speeds and unlimited data options.",
        rating = 4.9,
        plansAvailable = 3
    ),
    ISP(
        id = "isp-004",
        name = "SkyLink Broadband",
        logoUrl = "https://picsum.photos/seed/skylink/128/128",
        description = "Wide coverage area with various plans to suit your needs, from basic to ultra-fast.",
        rating = 4.2,
        plansAvailable = 6
    ),
    ISP(
        id = "isp-005",
        name = "WaveNet Solutions",
        logoUrl = "https://picsum.photos/seed/wavenet/128/128",
        description = "Innovative internet solutions with a focus on customer satisfaction and network stability.",
        rating = 4.6,
        plansAvailable = 4
    )
)

// --- COMPOSABLES ---

@Composable
fun IspPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Available Internet Service Providers",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (mockIsps.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mockIsps) { isp ->
                    IspCard(isp)
                }
            }
        } else {
            Text(
                text = "No ISPs found matching your criteria.",
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun IspCard(isp: ISP) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = isp.logoUrl),
                contentDescription = "ISP Logo",
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = isp.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = isp.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${isp.rating} ★ (${isp.plansAvailable} plans)",
                fontSize = 13.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun isp() {
    isp()
}
