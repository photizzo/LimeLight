package com.madememagic.limelight.domain.repository

import androidx.paging.PagingData
import com.madememagic.limelight.data.model.Genres
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.data.model.MovieItem
import com.madememagic.limelight.data.model.SearchBaseModel
import com.madememagic.limelight.data.model.artist.Artist
import com.madememagic.limelight.data.model.artist.ArtistDetail
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    fun recommendedMovie(movieId: Int): Flow<DataState<List<MovieItem>>>
    fun movieSearch(searchKey: String): Flow<DataState<SearchBaseModel>>
    fun genreList(): Flow<DataState<Genres>>
    fun movieCredit(movieId: Int): Flow<DataState<Artist>>
    fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>

    fun nowPlayingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun popularMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun topRatedMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun upcomingMoviePagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>
}
