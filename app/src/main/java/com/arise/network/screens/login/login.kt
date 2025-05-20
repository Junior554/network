import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arise.network.R
import com.arise.network.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun login(navController: NavController) {
    // State for email and password
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    val loginViewModel: AuthViewModel = viewModel()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    fun validateEmail(email: String): Boolean {
        // Simple email validation using regex
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        // Password must be at least 6 characters
        return password.length >= 6
    }

    // Function to handle form submission
    suspend fun onSubmit() {
        // Reset errors
        emailError = ""
        passwordError = ""

        // Validate form data
        if (!validateEmail(email.text)) {
            emailError = "Invalid email address"
        }
        if (!validatePassword(password.text)) {
            passwordError = "Password must be at least 6 characters"
        }

        if (emailError.isEmpty() && passwordError.isEmpty()) {
            // Simulate API call
            isSubmitting = true
            // Simulate a network delay
            kotlinx.coroutines.delay(1000)
            // Store user info (simulated)
//            val user = UserProfile(
//                id = email.text,
//                name = email.text.split('@')[0],
//                email = email.text,
//                avatarUrl = "https://picsum.photos/seed/${email.text}/200/200"
//            )
            // Simulate successful login
//            Toast.makeText(context, "Login Successful. Welcome, ${user.name}!", Toast.LENGTH_LONG).show()
            // Navigate to the dashboard (you could use Jetpack Navigation here)
        }
//        isSubmitting = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login to Royal ISP", style = MaterialTheme.typography.titleLarge)

        Image(
            painter = painterResource(id = R.drawable.disp),
            contentDescription = "regi",
            modifier = Modifier
                .background(color = Color.DarkGray)
                .size(300.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        if (emailError.isNotEmpty()) {
            Text(text = emailError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        if (passwordError.isNotEmpty()) {
            Text(text = passwordError, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Submit Button
        Button(
            onClick = { loginViewModel.login(email.text, password.text)

                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
//                        errorMessage = "" // Clear error message if successful
                navController.navigate(Routes.DASHBOARD)

//                if (!isSubmitting) {
//                    coroutineScope.launch {
//                        onSubmit()
//                    }
//                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            enabled = !isSubmitting
        ) {
            Text(if (isSubmitting) "Logging in..." else "Login")
        }

        // Sign Up Link
        TextButton(
            onClick = { navController.navigate(Routes.REGISTER) } // Navigate to Register screen
        ) {
            Text(text = "Don't have an account? Register")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun loginPreview() {
    login(rememberNavController())
}

