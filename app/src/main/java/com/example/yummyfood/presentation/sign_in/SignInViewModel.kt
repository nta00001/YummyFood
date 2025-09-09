package com.example.yummyfood.presentation.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> = _signInState

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                _signInState.value = SignInState.Success
            } catch (e: Exception) {
                _signInState.value = SignInState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }
}