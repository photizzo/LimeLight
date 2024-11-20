package com.madememagic.limelight.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.madememagic.limelight.data.local.KeeperDatabase
import com.madememagic.limelight.data.local.MoviesLocalDataSource
import com.madememagic.limelight.data.local.dao.MoviesDao
import com.madememagic.limelight.data.remote.AuthRemoteDataSource
import com.madememagic.limelight.data.remote.MoviesRemoteDataSource
import com.madememagic.limelight.data.remote.service.ApiService
import com.madememagic.limelight.data.repository.MoviesRepositoryImpl
import com.madememagic.limelight.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideKeeperDatabase(
        @ApplicationContext context: Context
    ): KeeperDatabase {
        return Room.databaseBuilder(
            context,
            KeeperDatabase::class.java,
            "limelight.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: KeeperDatabase) = database.moviesDao

    @Provides
    @Singleton
    fun provideNotesLocalDataSource(
        moviesDao: MoviesDao,
    ) = MoviesLocalDataSource(moviesDao)

    @Provides
    @Singleton
    fun provideNotesRepository(
        localDataSource: MoviesLocalDataSource,
        remoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideNotesRemoteDataSource(
        firestore: FirebaseFirestore,
        authRemoteDataSource: AuthRemoteDataSource,
        apiService: ApiService,
    ): MoviesRemoteDataSource = MoviesRemoteDataSource(firestore, authRemoteDataSource, apiService)
}
