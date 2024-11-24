package com.madememagic.limelight.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madememagic.limelight.data.model.moviedetail.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val isSelected: Boolean = false
) {
    fun toGenre(): Genre = Genre(
        id = id,
        name = name
    )

    companion object {
        fun fromGenre(genre: Genre, isSelected: Boolean = false): GenreEntity = GenreEntity(
            id = genre.id ?: 0,
            name = genre.name,
            isSelected = isSelected
        )
    }
}
