package com.madememagic.limelight.presentation.home

import com.madememagic.limelight.data.model.Movie

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val filteredMovies: List<Movie> = emptyList(),
    val searchSuggestions: List<Movie> = emptyList(),
)
