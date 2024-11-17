package com.madememagic.limelight.data.repository

import com.madememagic.limelight.data.remote.AuthRemoteDataSource
import com.madememagic.limelight.domain.repository.AppState
import com.madememagic.limelight.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImplementation @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(): AppState<Boolean> {
        return remoteDataSource.login()
    }

    override suspend fun logout(): AppState<Boolean> {
        return remoteDataSource.logout()
    }

    override suspend fun isLoggedIn(): Boolean {
        return remoteDataSource.isLoggedIn()
    }

    override fun getCurrentUserId(): String? {
        return remoteDataSource.getCurrentUserId()
    }
}
