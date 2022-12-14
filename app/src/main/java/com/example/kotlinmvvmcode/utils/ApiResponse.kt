package com.example.kotlinmvvmcode.utils


sealed class ApiResponse<out R> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}
