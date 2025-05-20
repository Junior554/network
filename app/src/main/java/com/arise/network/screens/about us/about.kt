package com.arise.network.screens.aboutus


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Us") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🌐 Royal Internet Service Providers",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = """
At Royal Internet Service Providers, we believe that internet connectivity should not just be fast, but transformative. Born out of a vision to bridge the digital divide, Royal ISP delivers high-performance, reliable, and affordable internet solutions designed to empower individuals, families, and businesses alike.

We stand for more than just megabytes and bandwidth—we stand for opportunity, freedom, and connection. Whether you're streaming in 4K, attending virtual classes, running a startup, or staying in touch with loved ones across the globe, our services ensure you’re always online, always ahead.

With a strong focus on customer satisfaction, cutting-edge technology, and 24/7 support, we continue to redefine what it means to be truly connected. We’re more than a service provider—we’re your digital partner, committed to excellence, innovation, and inclusivity.

Join the Royal family today and elevate your online experience with internet, the royal way. 👑
                """.trimIndent(),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}
