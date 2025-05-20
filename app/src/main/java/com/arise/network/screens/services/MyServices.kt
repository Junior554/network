package com.arise.network.screens.services

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.arise.network.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyServices(navController: NavHostController) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    var services by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Load services from Firebase
    LaunchedEffect(uid) {
        if (uid == null) {
            error = "User not logged in"
            isLoading = false
        } else {
            val ref = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("services")

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    services = snapshot.children.mapNotNull { it.getValue(String::class.java) }
                    isLoading = false
                }

                override fun onCancelled(errorSnapshot: DatabaseError) {
                    error = "Failed to load services: ${errorSnapshot.message}"
                    isLoading = false
                }
            })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Services") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.DASHBOARD) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = error ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                services.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("😔 No services found", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "You haven't added any services yet. Go to 'Add Services' and pick what you offer!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                else -> {
                    Column {
                        Text(
                            text = "Your Selected Internet Services",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(services) { service ->
                                ElevatedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.elevatedCardColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = service,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Button(onClick = {
                                                val newService = "$service (Updated)"
                                                val index = services.indexOf(service)
                                                if (index != -1 && uid != null) {
                                                    val ref = FirebaseDatabase.getInstance()
                                                        .getReference("users")
                                                        .child(uid)
                                                        .child("services")

                                                    ref.child(index.toString()).setValue(newService)
                                                        .addOnSuccessListener {
                                                            val updatedServices = services.toMutableList()
                                                            updatedServices[index] = newService
                                                            services = updatedServices
                                                            Toast.makeText(context, "Service updated successfully", Toast.LENGTH_SHORT).show()
                                                            navController.navigate(Routes.SERVICES)
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(context, "Failed to update service", Toast.LENGTH_SHORT).show()
                                                        }
                                                }
                                            }) {
                                                Text("Update Selected Services")
                                            }

                                            Button(onClick = {
                                                val index = services.indexOf(service)
                                                if (index != -1 && uid != null) {
                                                    val ref = FirebaseDatabase.getInstance()
                                                        .getReference("users")
                                                        .child(uid)
                                                        .child("services")

                                                    ref.child(index.toString()).removeValue()
                                                        .addOnSuccessListener {
                                                            services = services.filterNot { it == service }
                                                            Toast.makeText(context, "Service deleted successfully", Toast.LENGTH_SHORT).show()
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(context, "Failed to delete service", Toast.LENGTH_SHORT).show()
                                                        }
                                                }
                                            }) {
                                                Text("Delete Selected Services")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
