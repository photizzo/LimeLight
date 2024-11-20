package com.madememagic.limelight.domain.repository

sealed class DataState<out T> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error<out T>(val message: String) : DataState<T>()
    data object Loading : DataState<Nothing>()
}

interface AuthRepository {
    suspend fun login(): DataState<Boolean>
    suspend fun logout(): DataState<Boolean>
    suspend fun isLoggedIn(): Boolean
    fun getCurrentUserId(): String?
}
