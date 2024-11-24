package com.madememagic.limelight.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.data.model.MovieItem
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import com.madememagic.limelight.util.WorkManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val workManagerHelper: WorkManagerHelper
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()


    val nowPlayingMovies = moviesRepository.nowPlayingMoviePagingDataSource(null)
        .cachedIn(viewModelScope)

    val topRatedMovies = moviesRepository.topRatedMoviePagingDataSource(null)
        .cachedIn(viewModelScope)

    val upcomingMovies = moviesRepository.upcomingMoviePagingDataSource(null)
        .cachedIn(viewModelScope)

    val popularMovies = moviesRepository.popularMoviePagingDataSource(null)
        .cachedIn(viewModelScope)


    val combinedMovies: Flow<List<MovieSection>> = combine(
        nowPlayingMovies,
        topRatedMovies,
        upcomingMovies,
        popularMovies
    ) { nowPlaying, topRated, upcoming, popular ->
        listOf(
            MovieSection("Now Playing", nowPlaying),
            MovieSection("Top Rated", topRated),
            MovieSection("Upcoming", upcoming),
            MovieSection("Popular", popular)
        )
    }

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // Just to update the loading state, actual data comes from popularMovies flow
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )}
            }
        }
    }

}


data class MovieSection(
    val title: String,
    val movies: PagingData<MovieItem>
)
