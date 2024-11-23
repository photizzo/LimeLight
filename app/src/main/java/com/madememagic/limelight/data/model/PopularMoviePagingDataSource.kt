package com.madememagic.limelight.data.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.madememagic.limelight.data.remote.service.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PopularMoviePagingDataSource @Inject constructor(private val apiService: ApiService, private val genreId:String?) :
    PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val nextPage = params.key ?: 1
            val movieList = apiService.popularMovies(nextPage, genreId)
            LoadResult.Page(
                data = movieList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =  if (movieList.results.isNotEmpty()) movieList.page + 1 else  null
            )
        } catch (exception: IOException) {
            Log.e(TAG,"exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Log.e(TAG,"httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }

    companion object {
        const val TAG = "PopularMoviePagingDataSource"
    }
}
