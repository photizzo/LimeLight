package com.madememagic.limelight.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.madememagic.limelight.data.local.MoviesLocalDataSource
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
import com.madememagic.limelight.data.model.moviedetail.Genre
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import com.madememagic.limelight.data.remote.MoviesRemoteDataSource
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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

    override fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> =
        getSelectedGenresAsString().flatMapLatest { selectedGenres ->
            remoteDataSource.nowPlayingMoviePagingDataSource(selectedGenres.ifEmpty { null })
        }

    override fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> =
        getSelectedGenresAsString().flatMapLatest { selectedGenres ->
            remoteDataSource.popularMoviePagingDataSource(selectedGenres.ifEmpty { null })
        }

    override fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> =
        getSelectedGenresAsString().flatMapLatest { selectedGenres ->
            remoteDataSource.topRatedMoviePagingDataSource(selectedGenres.ifEmpty { null })
        }

    override fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> =
        getSelectedGenresAsString().flatMapLatest { selectedGenres ->
            remoteDataSource.upcomingMoviePagingDataSource(selectedGenres.ifEmpty { null })
        }
    override fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>> =
        remoteDataSource.genrePagingDataSource(genreId)

    override fun getSelectedGenres(): Flow<List<Genre>> {
        return localDataSource.getSelectedGenres()
    }

    override suspend fun updateGenreSelection(genreId: Int, isSelected: Boolean) {
        localDataSource.updateGenreSelection(genreId, isSelected)
    }

    override suspend fun saveGenres(genres: List<Genre>, isSelected: Boolean) {
        localDataSource.upsertGenres(genres, isSelected)
    }

    private fun getSelectedGenresAsString(): Flow<String> {
        return localDataSource.getSelectedGenres()
            .map { genres ->
                genres.mapNotNull { it.id }.joinToString("|")
            }
    }
}
