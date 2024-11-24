package com.madememagic.limelight.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.madememagic.limelight.data.local.entity.GenreEntity
import com.madememagic.limelight.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: String): Flow<MovieEntity?>

    @Upsert
    suspend fun updateMovie(note: MovieEntity)

    @Delete
    suspend fun deleteNote(note: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteNoteById(id: String)

    @Query("DELETE FROM movies")
    suspend fun deleteAllNotes()


    @Query("SELECT * FROM genres")
    fun getGenres(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genres WHERE isSelected = 1")
    fun getSelectedGenres(): Flow<List<GenreEntity>>

    @Upsert
    suspend fun upsertGenre(genre: GenreEntity)

    @Upsert
    suspend fun upsertGenres(genres: List<GenreEntity>)

    @Query("UPDATE genres SET isSelected = :isSelected WHERE id = :genreId")
    suspend fun updateGenreSelection(genreId: Int, isSelected: Boolean)

    @Query("DELETE FROM genres")
    suspend fun deleteAllGenres()
}
