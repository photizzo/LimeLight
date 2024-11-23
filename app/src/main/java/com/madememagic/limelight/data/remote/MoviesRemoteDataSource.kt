package com.madememagic.limelight.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.FirebaseFirestore
import com.madememagic.limelight.data.model.GenrePagingDataSource
import com.madememagic.limelight.data.model.Genres
import com.madememagic.limelight.data.model.MovieItem
import com.madememagic.limelight.data.model.NowPlayingMoviePagingDataSource
import com.madememagic.limelight.data.model.PopularMoviePagingDataSource
import com.madememagic.limelight.data.model.SearchBaseModel
import com.madememagic.limelight.data.model.TopRatedMoviePagingDataSource
import com.madememagic.limelight.data.model.UpcomingMoviePagingDataSource
import com.madememagic.limelight.data.model.artist.Artist
import com.madememagic.limelight.data.model.artist.ArtistDetail
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import com.madememagic.limelight.data.remote.service.ApiService
import com.madememagic.limelight.domain.repository.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val apiService: ApiService,
) {
    private val userId: String?
        get() = authRemoteDataSource.getCurrentUserId()

    fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.movieDetail(movieId)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.recommendedMovie(movieId)
                emit(DataState.Success(searchResult.results))

            } catch (e: Exception) {
                emit(DataState.Error(e.localizedMessage))
            }
        }


    fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.searchMovie(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
        emit(DataState.Loading)
        try {
            val artistResult = apiService.movieCredit(movieId)
            emit(DataState.Success(artistResult))

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> = flow {
        emit(DataState.Loading)
        try {
            val artistDetailResult = apiService.artistDetail(personId)
            emit(DataState.Success(artistDetailResult))

        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage))
        }
    }

    fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { NowPlayingMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { PopularMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { TopRatedMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { UpcomingMoviePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { GenrePagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 20)
    ).flow

}
