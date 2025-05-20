package com.arise.enetwork.repository



import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
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
        auth.signOut()
    }
}


