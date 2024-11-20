package com.madememagic.limelight.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madememagic.limelight.data.model.moviedetail.Genre
import com.madememagic.limelight.domain.repository.DataState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onGenreSelected: () -> Unit
) {
    val genresState = viewModel.genres.collectAsState().value
    var selectedGenres by remember { mutableStateOf(setOf<Genre>()) }
    var prioritizedGenres by remember { mutableStateOf(setOf<Genre>()) }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.navigationBarsPadding()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose genres you love",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Tap once to select, tap twice to prioritize",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            when (val genresList = genresState) {
                is DataState.Loading -> {
                    CircularProgressIndicator()
                }

                is DataState.Success -> {
                    Column (
                        modifier = Modifier.weight(1f).verticalScroll(scrollState)
                    ) {
                        genresList.data.genres.chunked(3).forEach { row ->
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                row.forEach { genre ->
                                    GenreCircle(
                                        genre = genre,
                                        isSelected = genre in selectedGenres,
                                        isPrioritized = genre in prioritizedGenres,
                                        onClick = {
                                            if (genre in selectedGenres) {
                                                if (genre in prioritizedGenres) {
                                                    prioritizedGenres = prioritizedGenres - genre
                                                    selectedGenres = selectedGenres - genre
                                                } else {
                                                    prioritizedGenres = prioritizedGenres + genre
                                                }
                                            } else {
                                                selectedGenres = selectedGenres + genre
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onGenreSelected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        enabled = selectedGenres.isNotEmpty()
                    ) {
                        Text("Continue")
                    }
                }

                is DataState.Error -> {
                    Text(
                        text = "Error loading genres: ${genresList.message}",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun GenreCircle(
    genre: Genre,
    isSelected: Boolean,
    isPrioritized: Boolean,
    onClick: () -> Unit
) {
    val circleSize = if (isPrioritized) 120.dp else if (isSelected) 110.dp else 100.dp

    Surface(
        shape = CircleShape,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.tertiary else Color.Gray),
        color = when {
            isPrioritized -> MaterialTheme.colorScheme.tertiary
            isSelected -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
            else -> Color.Transparent
        },
        modifier = Modifier
            .size(circleSize)
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = genre.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
