package com.madememagic.limelight.data.repository

import com.madememagic.limelight.data.remote.AuthRemoteDataSource
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImplementation @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(): DataState<Boolean> {
        return remoteDataSource.login()
    }

    override suspend fun logout(): DataState<Boolean> {
        return remoteDataSource.logout()
    }

    override suspend fun isLoggedIn(): Boolean {
        return remoteDataSource.isLoggedIn()
    }

    override fun getCurrentUserId(): String? {
        return remoteDataSource.getCurrentUserId()
    }
}
