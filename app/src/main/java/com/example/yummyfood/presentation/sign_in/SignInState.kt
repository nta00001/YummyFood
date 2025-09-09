package com.example.yummyfood.presentation.sign_in

// Lớp đại diện cho các trạng thái của UI
sealed class SignInState {
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}