package com.madememagic.limelight.presentation.home

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import com.madememagic.limelight.R
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.data.model.MovieItem
import com.madememagic.limelight.navigation.KeeperDestination
import com.madememagic.limelight.presentation.auth.AuthViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.crossfade


@Composable
fun MainContainer(
    currentDestination: KeeperDestination?,
    onNavigate: (Movie?) -> Unit,
    onSettingsClick: () -> Unit,
    logout: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination is KeeperDestination.Home,
                    onClick = onSettingsClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentDestination is KeeperDestination.ManageUserPreference,
                    onClick = onSettingsClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentDestination) {
                is KeeperDestination.Home -> HomeScreen(
                    logout = logout,
                    onNavigate = onNavigate
                )
                is KeeperDestination.ManageUserPreference -> SettingsScreen()
                else -> {}
            }
        }
    }
}

@Composable
fun HomeScreen(
    logout: () -> Unit,
    onNavigate: (Movie?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val isUserAuthenticated by authViewModel.isUserAuthenticated.collectAsStateWithLifecycle()

    Log.e("TAG", "Popular movies home screen")


    LaunchedEffect(isUserAuthenticated) {
        if (!isUserAuthenticated) {
            logout()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        HomeContent(
                            onMovieClick = { onNavigate(it) },
                        )
                    }


                }
            }
        }
    }
}

@Composable
private fun HomeContent(
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val nowPlayingMovies = viewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            // Show loading if any section is in initial loading state
            nowPlayingMovies.loadState.refresh is LoadState.Loading ||
                    topRatedMovies.loadState.refresh is LoadState.Loading ||
                    upcomingMovies.loadState.refresh is LoadState.Loading ||
                    popularMovies.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Show error if any section has an error
            nowPlayingMovies.loadState.refresh is LoadState.Error ||
                    topRatedMovies.loadState.refresh is LoadState.Error ||
                    upcomingMovies.loadState.refresh is LoadState.Error ||
                    popularMovies.loadState.refresh is LoadState.Error -> {
                val error = when {
                    nowPlayingMovies.loadState.refresh is LoadState.Error ->
                        (nowPlayingMovies.loadState.refresh as LoadState.Error).error
                    topRatedMovies.loadState.refresh is LoadState.Error ->
                        (topRatedMovies.loadState.refresh as LoadState.Error).error
                    upcomingMovies.loadState.refresh is LoadState.Error ->
                        (upcomingMovies.loadState.refresh as LoadState.Error).error
                    else -> (popularMovies.loadState.refresh as LoadState.Error).error
                }
                Text(
                    text = error.localizedMessage ?: "Error loading movies",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Now Playing Movies
                    items(nowPlayingMovies.itemCount) { index ->
                        nowPlayingMovies[index]?.let { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    onMovieClick(Movie(
                                        id = movie.id.toString(),
                                        title = movie.title
                                    ))
                                }
                            )
                        }
                    }

                    // Top Rated Movies
                    items(topRatedMovies.itemCount) { index ->
                        topRatedMovies[index]?.let { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    onMovieClick(Movie(
                                        id = movie.id.toString(),
                                        title = movie.title
                                    ))
                                }
                            )
                        }
                    }

                    // Upcoming Movies
                    items(upcomingMovies.itemCount) { index ->
                        upcomingMovies[index]?.let { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    onMovieClick(Movie(
                                        id = movie.id.toString(),
                                        title = movie.title
                                    ))
                                }
                            )
                        }
                    }

                    // Popular Movies
                    items(popularMovies.itemCount) { index ->
                        popularMovies[index]?.let { movie ->
                            MovieCard(
                                movie = movie,
                                onClick = {
                                    onMovieClick(Movie(
                                        id = movie.id.toString(),
                                        title = movie.title
                                    ))
                                }
                            )
                        }
                    }

                    // Show loading or error state if any section is loading more or has error
                    when {
                        nowPlayingMovies.loadState.append is LoadState.Loading ||
                                topRatedMovies.loadState.append is LoadState.Loading ||
                                upcomingMovies.loadState.append is LoadState.Loading ||
                                popularMovies.loadState.append is LoadState.Loading -> {
                            item(span = { GridItemSpan(2) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        nowPlayingMovies.loadState.append is LoadState.Error ||
                                topRatedMovies.loadState.append is LoadState.Error ||
                                upcomingMovies.loadState.append is LoadState.Error ||
                                popularMovies.loadState.append is LoadState.Error -> {
                            item(span = { GridItemSpan(2) }) {
                                val error = when {
                                    nowPlayingMovies.loadState.append is LoadState.Error ->
                                        (nowPlayingMovies.loadState.append as LoadState.Error).error
                                    topRatedMovies.loadState.append is LoadState.Error ->
                                        (topRatedMovies.loadState.append as LoadState.Error).error
                                    upcomingMovies.loadState.append is LoadState.Error ->
                                        (upcomingMovies.loadState.append as LoadState.Error).error
                                    else -> (popularMovies.loadState.append as LoadState.Error).error
                                }
                                Text(
                                    text = error.localizedMessage ?: "Error loading more movies",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieCard(
    movie: MovieItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.67f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
