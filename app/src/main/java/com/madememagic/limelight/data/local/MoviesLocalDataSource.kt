package com.madememagic.limelight.data.local

import com.madememagic.limelight.data.local.dao.MoviesDao
import com.madememagic.limelight.data.local.entity.GenreEntity
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.data.model.moviedetail.Genre
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


    fun getGenres(): Flow<List<Genre>> {
        return moviesDao.getGenres().map { entities ->
            entities.map { it.toGenre() }
        }
    }

    fun getSelectedGenres(): Flow<List<Genre>> {
        return moviesDao.getSelectedGenres().map { entities ->
            entities.map { it.toGenre() }
        }
    }

    suspend fun upsertGenre(genre: Genre, isSelected: Boolean = false) {
        moviesDao.upsertGenre(GenreEntity.fromGenre(genre, isSelected))
    }

    suspend fun upsertGenres(genres: List<Genre>, isSelected: Boolean) {
        moviesDao.upsertGenres(genres.map { GenreEntity.fromGenre(it, isSelected) })
    }

    suspend fun updateGenreSelection(genreId: Int, isSelected: Boolean) {
        moviesDao.updateGenreSelection(genreId, isSelected)
    }

    suspend fun deleteAllGenres() {
        moviesDao.deleteAllGenres()
    }



}
