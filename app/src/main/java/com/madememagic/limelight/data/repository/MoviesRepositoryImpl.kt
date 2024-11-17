package com.madememagic.limelight.data.repository

import com.madememagic.limelight.data.local.MoviesLocalDataSource
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.data.remote.MoviesRemoteDataSource
import com.madememagic.limelight.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override fun getMovies(): Flow<List<Movie>> {
        return localDataSource.getMovies()
    }

    override fun getMoviesById(id: String): Flow<Movie?> {
        return localDataSource.getNoteById(id)
    }
}
