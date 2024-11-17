package com.madememagic.limelight.domain.repository

sealed class AppState<T> {
    data class Success<T>(val data: T) : AppState<T>()
    data class Error<T>(val message: String) : AppState<T>()
    class Loading<T> : AppState<T>()
}

interface AuthRepository {
    suspend fun login(): AppState<Boolean>
    suspend fun logout(): AppState<Boolean>
    suspend fun isLoggedIn(): Boolean
    fun getCurrentUserId(): String?
}
