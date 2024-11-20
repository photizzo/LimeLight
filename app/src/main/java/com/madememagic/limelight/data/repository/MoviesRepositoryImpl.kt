package com.madememagic.limelight.data.repository

import com.madememagic.limelight.data.local.MoviesLocalDataSource
import com.madememagic.limelight.data.model.Genres
import com.madememagic.limelight.data.model.MovieItem
import com.madememagic.limelight.data.model.SearchBaseModel
import com.madememagic.limelight.data.model.artist.Artist
import com.madememagic.limelight.data.model.artist.ArtistDetail
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import com.madememagic.limelight.data.remote.MoviesRemoteDataSource
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {
    override fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> {
        return remoteDataSource.movieDetail(movieId)
    }

    override fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>> {
        return remoteDataSource.recommendedMovie(movieId)
    }

    override fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>> {
        return remoteDataSource.movieSearch(searchKey)
    }

    override fun genreList(): Flow<DataState<Genres>> {
       return remoteDataSource.genreList()
    }

    override fun movieCredit(movieId: Int): Flow<DataState<Artist>> {
        return remoteDataSource.movieCredit(movieId)
    }

    override fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> {
        return remoteDataSource.artistDetail(personId)
    }
}
