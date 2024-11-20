package com.madememagic.limelight.data.remote

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.madememagic.limelight.BuildConfig
import com.madememagic.limelight.domain.repository.DataState
import com.madememagic.limelight.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : AuthRepository {

    private companion object {
        private const val TAG = "AuthRemoteDataSource"
    }

    override suspend fun login(): DataState<Boolean> {
        return googleSignIn(context)
            .map { result ->
                result.fold(
                    onSuccess = {
                        DataState.Success(true)
                    },
                    onFailure = { e ->
                        DataState.Error(e.message ?: "Sign in failed")
                    }
                )
            }
            .first()
    }

    override suspend fun logout(): DataState<Boolean> {
        return try {
            firebaseAuth.signOut()
            DataState.Success(true)
        } catch (e: Exception) {
            Log.e(TAG, "signOut failed: ${e.message}")
            DataState.Error(e.message ?: "Sign out failed")
        }
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun googleSignIn(context: Context): Flow<Result<AuthResult>> = callbackFlow {
        try {
            // Initialize Credential Manager
            val credentialManager = CredentialManager.create(context)

            // Generate a nonce
            val ranNonce = UUID.randomUUID().toString()
            val bytes = ranNonce.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            val hashedNonce = digest.fold("") { str, it ->
                str + "%02x".format(it)
            }

            // Set up Google ID option
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                .setNonce(hashedNonce)
                .build()

            // Create credential request
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            // Get the credential result
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val authCredential = GoogleAuthProvider
                    .getCredential(googleIdTokenCredential.idToken, null)

                val authResult = firebaseAuth
                    .signInWithCredential(authCredential)
                    .await()

                trySend(Result.success(authResult))
            } else {
                trySend(Result.failure(RuntimeException("Invalid credential type")))
            }
        } catch (e: GetCredentialCancellationException) {
            trySend(Result.failure(Exception("Sign-in cancelled")))
        } catch (e: Exception) {
            Log.e(TAG, "Google sign in failed", e)
            trySend(Result.failure(e))
        }

        awaitClose()
    }
}
