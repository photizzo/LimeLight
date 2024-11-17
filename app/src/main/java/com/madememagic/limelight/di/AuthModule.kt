package com.madememagic.limelight.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.madememagic.limelight.data.remote.AuthRemoteDataSource
import com.madememagic.limelight.data.repository.AuthRepositoryImplementation
import com.madememagic.limelight.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context
    ): AuthRemoteDataSource {
        return AuthRemoteDataSource(firebaseAuth, context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImplementation(remoteDataSource)
    }
}
