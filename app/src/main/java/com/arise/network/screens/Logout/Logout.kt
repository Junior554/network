package com.arise.network.screens.Logout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arise.network.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun LogoutScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val uid = user?.uid
    val databaseRef = uid?.let {
        FirebaseDatabase.getInstance().getReference("users").child(it)
    }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(true) }

    // Fetch user info
    LaunchedEffect(uid) {
        if (databaseRef != null) {
            databaseRef.get().addOnSuccessListener { snapshot ->
                name = snapshot.child("name").getValue(String::class.java) ?: "N/A"
                email = snapshot.child("email").getValue(String::class.java) ?: "N/A"
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("User Info", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Name: $name")
            Text("Email: $email")

            Spacer(modifier = Modifier.height(32.dp))

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("😢 Are you sure?") },
                    text = {
                        Text(
                            "Logging out will delete your account and all associated data. You can also choose to stay or go back to the dashboard.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            if (user != null && databaseRef != null) {
                                // Delete user data from Realtime Database
                                databaseRef.removeValue().addOnCompleteListener { dataTask ->
                                    if (dataTask.isSuccessful) {
                                        // Delete user from Firebase Auth
                                        user.delete().addOnCompleteListener { deleteTask ->
                                            if (deleteTask.isSuccessful) {
                                                auth.signOut()
                                                navController.navigate(Routes.LOGIN) {
                                                    popUpTo(0)
                                                }
                                            } else {
                                                // Fallback if delete failed (e.g., recent login required)
                                                navController.navigate(Routes.LOGIN) {
                                                    popUpTo(0)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }) {
                            Text("Log Out & Delete")
                        }
                    },
                    dismissButton = {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = {
                                navController.navigate(Routes.DASHBOARD) {
                                    popUpTo(0)
                                }
                            }) {
                                Text("Go to Dashboard")
                            }
                        }
                    }
                )
            } else {
                Text(
                    "You are still logged in 😊",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
