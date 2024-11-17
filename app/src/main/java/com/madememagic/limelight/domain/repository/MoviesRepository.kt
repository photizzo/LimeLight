package com.madememagic.limelight.domain.repository

import com.madememagic.limelight.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<List<Movie>>
    fun getMoviesById(id: String): Flow<Movie?>
}
