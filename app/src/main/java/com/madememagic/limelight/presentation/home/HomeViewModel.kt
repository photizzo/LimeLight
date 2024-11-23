package com.madememagic.limelight.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import com.madememagic.limelight.util.WorkManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val workManagerHelper: WorkManagerHelper
) : ViewModel() {

    init {
        getMovies()
    }

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    val nowPlayingMovies = moviesRepository.nowPlayingMoviePagingDataSource(null)
    val popularMovies = moviesRepository.popularMoviePagingDataSource(null)
    val topRatedMovies = moviesRepository.topRatedMoviePagingDataSource(null)
    val upcomingMovies = moviesRepository.upcomingMoviePagingDataSource(null)

    fun onSearchQueryChange(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(searchQuery = query) }
            if (query.isNotEmpty()) {
                moviesRepository.movieSearch(query)
                    .collect { result ->
                        when (result) {
                            is DataState.Success -> {
//                                _state.update { it.copy(
//                                    searchSuggestions = result.data.results.map { searchItem ->
//
//                                    }
//                                )}
                            }
                            else -> {}
                        }
                    }
            } else {
                _state.update { it.copy(searchSuggestions = emptyList()) }
            }
        }
    }


    private fun getMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

//            moviesRepository.getMovies()
//                .catch { e ->
//                    _state.update {
//                        it.copy(
//                            isLoading = false,
//                            error = e.message
//                        )
//                    }
//                }
//                .collect { movies ->
//                    _state.update {
//                        it.copy(
//                            movies = movies,
//                            filteredMovies = filterMovies(movies, it.searchQuery),
//                            isLoading = false,
//                            error = null
//                        )
//                    }
        }
//        }
    }

}
