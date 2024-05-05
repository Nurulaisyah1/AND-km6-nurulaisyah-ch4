package com.foodapps.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<FirebaseUser>>()
    val registrationResult: LiveData<Result<FirebaseUser>> = _registrationResult

    fun registerUser(email: String, password: String, name: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _registrationResult.value = Result.success(user)
                            } else {
                                _registrationResult.value = Result.failure(updateTask.exception!!)
                            }
                        }
                } else {
                    _registrationResult.value = Result.failure(task.exception!!)
                }
            }
    }
}

