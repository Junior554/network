

import android.util.Log
import com.arise.enetwork.repository.AuthRepository


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isRegistered by mutableStateOf<Boolean?>(null)
        private set

    var isLoggedIn by mutableStateOf<Boolean?>(null)
        private set

    var isupdated by mutableStateOf<String?>(null)
        private set

    fun register(email: String, password: String, onResult: (Boolean) -> Unit = {}) {
        repository.register(email, password) { success ->
            isRegistered = success
            onResult(success)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit = {}) {
        repository.login(email, password) { success ->
            isLoggedIn = success
            onResult(success)
        }
    }

    fun Form(email: String, password: String, onResult: (Boolean) -> Unit = {}) {
        repository.login(email, password) { success ->
            isupdated = success.toString()
            onResult(success)

        }
    }

    fun updateCurrentUserEmailAndPassword(newEmail: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.updateEmail(newEmail)?.addOnCompleteListener { emailTask ->
            if (emailTask.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { passwordTask ->
                    if (passwordTask.isSuccessful) {
                        Log.d("Firebase", "Email and password updated.")
                    } else {
                        Log.e("Firebase", "Failed to update password.", passwordTask.exception)
                    }
                }
            } else {
                Log.e("Firebase", "Failed to update email.", emailTask.exception)
            }
        }
    }


    fun logout() {
        repository.logout()
        isLoggedIn = false
    }
}


