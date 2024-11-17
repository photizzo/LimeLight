package com.madememagic.limelight.data.local

import com.madememagic.limelight.data.local.dao.MoviesDao
import com.madememagic.limelight.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) {
    fun getMovies(): Flow<List<Movie>> {
        return moviesDao.getMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }

    fun getNoteById(id: String): Flow<Movie?> {
        return moviesDao.getMovieById(id).map { entity ->
            entity?.toMovie()
        } ?: flowOf(null)
    }
}
