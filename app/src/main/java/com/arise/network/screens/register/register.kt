// Imports
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arise.network.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCounty by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

    val kenyanCounties = listOf(
        "Mombasa", "Kwale", "Kilifi", "Tana River", "Lamu", "Taita Taveta",
        "Garissa", "Wajir", "Mandera", "Marsabit", "Isiolo", "Meru", "Tharaka-Nithi",
        "Embu", "Kitui", "Machakos", "Makueni", "Nyandarua", "Nyeri", "Kirinyaga",
        "Murang'a", "Kiambu", "Turkana", "West Pokot", "Samburu", "Trans Nzoia",
        "Uasin Gishu", "Elgeyo Marakwet", "Nandi", "Baringo", "Laikipia", "Nakuru",
        "Narok", "Kajiado", "Kericho", "Bomet", "Kakamega", "Vihiga", "Bungoma",
        "Busia", "Siaya", "Kisumu", "Homa Bay", "Migori", "Kisii", "Nyamira", "Nairobi"
    )

    fun validateForm(): Boolean {
        return name.text.isNotBlank() &&
                email.text.isNotBlank() &&
                password.text == confirmPassword.text &&
                password.text.length >= 6 &&
                selectedCounty.isNotBlank()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text("Register And Create Account", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        InputField("Full Name", name, onValueChange = { name = it }, enabled = !isSubmitting)
        InputField("Email", email, onValueChange = { email = it }, enabled = !isSubmitting)
        InputField(
            "Password",
            password,
            isPassword = true,
            onValueChange = { password = it },
            enabled = !isSubmitting
        )
        InputField(
            "Confirm Password",
            confirmPassword,
            isPassword = true,
            onValueChange = { confirmPassword = it },
            enabled = !isSubmitting
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Select County", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(6.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCounty,
                onValueChange = {},
                readOnly = true,
                label = { Text("County") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                kenyanCounties.forEach { county ->
                    DropdownMenuItem(
                        text = { Text(county) },
                        onClick = {
                            selectedCounty = county
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (!validateForm()) {
                    Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                scope.launch {
                    isSubmitting = true
                    try {
                        val authResult = auth.createUserWithEmailAndPassword(email.text, password.text).await()
                        val uid = authResult.user?.uid ?: throw Exception("User UID is null.")

                        db.collection("users").document(uid).set(
                            mapOf(
                                "name" to name.text,
                                "email" to email.text,
                                "county" to selectedCounty
                            )
                        ).await()

                        Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(
                            context,
                            "Error: ${e.localizedMessage ?: "Unknown error"}",
                            Toast.LENGTH_LONG
                        ).show()
                    } finally {
                        isSubmitting = false
                    }
                }
            },
            enabled = !isSubmitting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    isPassword: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        enabled = enabled
    )
}
