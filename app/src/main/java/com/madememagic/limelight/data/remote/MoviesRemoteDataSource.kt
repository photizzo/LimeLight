package com.madememagic.limelight.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.madememagic.limelight.data.model.Movie
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRemoteDataSource: AuthRemoteDataSource,
) {
    private val userId: String?
        get() = authRemoteDataSource.getCurrentUserId()

    suspend fun syncMovie(movie: Movie) {

    }

}
