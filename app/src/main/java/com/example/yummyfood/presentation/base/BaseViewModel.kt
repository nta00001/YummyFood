// path: app/src/main/java/com/example/yummyfood/presentation/base/BaseViewModel.kt
package com.example.yummyfood.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // Một StateFlow chung để quản lý trạng thái loading
    protected val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    // Một StateFlow chung để xử lý lỗi
    protected val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    /**
     * Hàm helper để thực thi một coroutine trong viewModelScope
     * và tự động xử lý trạng thái loading.
     */
    protected fun <T> execute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = call()
                onSuccess(result)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unknown error occurred"
                _error.value = errorMessage
                onError(errorMessage)
            } finally {
                _isLoading.value = false
            }
        }
    }
}