package com.madememagic.limelight.presentation.moviedetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            moviesRepository.movieDetail(movieId).collect { state ->
                when (state) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }
                    is DataState.Success -> {
                        _movieDetail.value = state.data
                        _isLoading.value = false
                    }
                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}
