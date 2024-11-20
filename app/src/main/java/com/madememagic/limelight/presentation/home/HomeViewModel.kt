package com.madememagic.limelight.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madememagic.limelight.data.model.Movie
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

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getMovies()
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


    fun onSearchQueryChange(query: String) {

    }


    private fun filterMovies(movies: List<Movie>, query: String): List<Movie> {
        if (query.isBlank()) return movies

        return movies.filter { note ->
            note.title.contains(query, ignoreCase = true)
        }
    }
}
