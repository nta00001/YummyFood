package com.example.yummyfood.presentation.sign_up

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>(SignUpState.Idle)
    val signUpState: MutableLiveData<SignUpState> = _signUpState

    fun signUpUser(email: String , password: String, fullName: String, phoneNumber: String) {

        if (email.isBlank() || password.isBlank()) {
            _signUpState.value = SignUpState.Error("Email and password cannot be empty.")
            return
        }

        _signUpState.value = SignUpState.Loading
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Optionally, you can update the user's profile with full name and phone number here
                    _signUpState.value = SignUpState.Success
                } else {
                    _signUpState.value = SignUpState.Error(task.exception?.message ?: "Sign up failed")
                }
            }

    }
}