package com.arise.network.screens.services

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arise.network.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddServices(navController: NavController) {
    val context = LocalContext.current
    val serviceOptions = listOf(
        "Fiber Internet",
        "Mobile Data",
        "Home Wi-Fi",
        "Satellite Internet",
        "5G Broadband",
        "DSL",
        "Cable Internet",
        "Fixed Wireless",
        "Wifi Management",
        "Mbps Upgrade",
        "Purchase Internet Accessories"
    )

    val selectedServices = remember { mutableStateMapOf<String, Boolean>() }
    serviceOptions.forEach {
        if (it !in selectedServices) {
            selectedServices[it] = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Internet Services you Want to be Provided ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tap to select the services you provide:",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Service Options
            serviceOptions.forEach { service ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedServices[service] = !(selectedServices[service] ?: false) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Checkbox(
                            checked = selectedServices[service] == true,
                            onCheckedChange = { selectedServices[service] = it }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(service)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedServices.values.any { it }) {
                Text(
                    text = "Selected: ${selectedServices.filterValues { it }.keys.joinToString(", ")}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val user = FirebaseAuth.getInstance().currentUser
                    val selectedList = selectedServices.filterValues { it }.keys.toList()

                    if (selectedList.isEmpty()) {
                        Toast.makeText(context, "Please select at least one service", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    user?.uid?.let { uid ->
                        val servicesRef = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(uid)
                            .child("services")

                        servicesRef.setValue(selectedList).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Services Successfully Added", Toast.LENGTH_SHORT).show()
                                navController.navigate(Routes.MY_SERVICES)
                            } else {
                                Toast.makeText(context, "Failed to save services", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to my Services")
            }
        }
    }
}
