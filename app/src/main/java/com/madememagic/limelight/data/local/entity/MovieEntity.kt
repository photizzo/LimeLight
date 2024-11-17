package com.madememagic.limelight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madememagic.limelight.data.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
        )
    }
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
    )
}
