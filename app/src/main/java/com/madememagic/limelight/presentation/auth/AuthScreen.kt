package com.madememagic.limelight.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madememagic.limelight.R
import com.madememagic.limelight.domain.repository.AppState

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onAuthSuccess: () -> Unit
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isUserAuthenticated by viewModel.isUserAuthenticated.collectAsStateWithLifecycle()

    LaunchedEffect(isUserAuthenticated) {
        if (isUserAuthenticated) {
            onAuthSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "to LimeLight",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        when (authState) {
            is AppState.Loading -> {
                CircularProgressIndicator()
            }
            is AppState.Error -> {
                GoogleSignInButton(
                    onClick = { viewModel.signInWithGoogle() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (authState as AppState.Error<Boolean>).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                GoogleSignInButton(
                    onClick = { viewModel.signInWithGoogle() }
                )
            }
        }
    }
}

@Composable
private fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icons8_google),
                contentDescription = "Google Logo",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google")
        }
    }
}
