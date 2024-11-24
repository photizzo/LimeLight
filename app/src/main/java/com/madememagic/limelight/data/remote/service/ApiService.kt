package com.madememagic.limelight.data.remote.service

import com.madememagic.limelight.BuildConfig
import com.madememagic.limelight.data.model.BaseModelMovie
import com.madememagic.limelight.data.model.BaseModelTvSeries
import com.madememagic.limelight.data.model.Genres
import com.madememagic.limelight.data.model.SearchBaseModel
import com.madememagic.limelight.data.model.artist.Artist
import com.madememagic.limelight.data.model.artist.ArtistDetail
import com.madememagic.limelight.data.model.moviedetail.MovieDetail
import com.madememagic.limelight.data.model.tvseries.TvSeriesDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun nowPlayingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("movie/top_rated")
    suspend fun topRatedMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("movie/upcoming")
    suspend fun upcomingMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("movie/{movieId}")
    suspend fun movieDetail(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieDetail

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("search/movie?page=1&include_adult=false")
    suspend fun searchMovie(
        @Query("query") searchKey: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): SearchBaseModel

    @GET("genre/movie/list")
    suspend fun genreList(@Query("api_key") apiKey: String = BuildConfig.API_KEY): Genres

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelMovie

    @GET("movie/{movieId}/credits")
    suspend fun movieCredit(
        @Path("movieId") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Artist

    @GET("person/{personId}")
    suspend fun artistDetail(
        @Path("personId") personId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ArtistDetail

    @GET("tv/airing_today")
    suspend fun airingTodayTvSeries(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelTvSeries

    @GET("tv/on_the_air")
    suspend fun onTheAirTvSeries(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelTvSeries

    @GET("tv/popular")
    suspend fun popularTvSeries(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelTvSeries

    @GET("tv/top_rated")
    suspend fun topRatedTvSeries(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelTvSeries

    @GET("tv/{seriesId}")
    suspend fun tvSeriesDetail(
        @Path("seriesId") seriesId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): TvSeriesDetail

    @GET("tv/{seriesId}/recommendations")
    suspend fun recommendedTvSeries(
        @Path("seriesId") seriesId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): BaseModelTvSeries

    @GET("tv/{seriesId}/credits")
    suspend fun tvSeriesCredit(
        @Path("seriesId") seriesId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): Artist

    @GET("search/tv?page=1&include_adult=false")
    suspend fun searchTvSeries(
        @Query("query") searchKey: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): SearchBaseModel
}
