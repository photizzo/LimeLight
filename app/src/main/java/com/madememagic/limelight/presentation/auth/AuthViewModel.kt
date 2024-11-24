package com.madememagic.limelight.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madememagic.limelight.data.model.Genres
import com.madememagic.limelight.data.model.moviedetail.Genre
import com.madememagic.limelight.domain.repository.AuthRepository
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import com.madememagic.limelight.util.NotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val moviesRepository: MoviesRepository,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _authState = MutableStateFlow<DataState<Boolean>?>(null)
    val authState = _authState.asStateFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated = _isUserAuthenticated.asStateFlow()

    private val _genres = MutableStateFlow<DataState<Genres>?>(null)
    val genres = _genres.asStateFlow()

    init {
        checkAuthStatus()
        genreList()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val isLoggedIn = authRepository.isLoggedIn()
            _isUserAuthenticated.value = isLoggedIn
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            _authState.value = DataState.Loading

            when (val result = authRepository.login()) {
                is DataState.Success -> {
                    _authState.value = result

                    // Show appropriate notification
                    notificationManager.showAuthNotification(
                        isSuccess = true,
                        isNewUser = true
                    )

                    checkAuthStatus()
                }

                is DataState.Error -> {
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


    fun saveSelectedGenre(genres: List<Genre>) {
        viewModelScope.launch {
            moviesRepository.saveGenres(genres, isSelected = true)
        }

    }

    private fun genreList() {
        viewModelScope.launch {
            moviesRepository.genreList().onEach {
                _genres.value = it
                Log.e("TAG", "This is the genres result $it")
            }.launchIn(viewModelScope)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _authState.value = DataState.Loading
            _authState.value = authRepository.logout()
            checkAuthStatus()
        }
    }
}
