package com.madememagic.limelight.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madememagic.limelight.domain.repository.AppState
import com.madememagic.limelight.domain.repository.AuthRepository
import com.madememagic.limelight.domain.repository.MoviesRepository
import com.madememagic.limelight.util.NotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val notesRepository: MoviesRepository,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AppState<Boolean>?>(null)
    val authState = _authState.asStateFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn()
            _isUserAuthenticated.value = isLoggedIn
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _authState.value = AppState.Loading()

            when (val result = authRepository.login()) {
                is AppState.Success -> {
                    _authState.value = result

                    // Show appropriate notification
                    notificationManager.showAuthNotification(
                        isSuccess = true,
                        isNewUser = true
                    )

                    checkAuthStatus()
                }

                is AppState.Error -> {
                    _authState.value = result
                    notificationManager.showAuthNotification(
                        isSuccess = false,
                        isNewUser = false
                    )
                }

                else -> _authState.value = result
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _authState.value = AppState.Loading()
            _authState.value = authRepository.logout()
            checkAuthStatus()
        }
    }
}
