package com.madememagic.limelight.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.madememagic.limelight.data.local.dao.MoviesDao
import com.madememagic.limelight.data.local.entity.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1
)
abstract class KeeperDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}
