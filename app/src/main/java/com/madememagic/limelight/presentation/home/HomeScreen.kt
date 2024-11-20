package com.madememagic.limelight.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madememagic.limelight.R
import com.madememagic.limelight.data.model.Movie
import com.madememagic.limelight.navigation.KeeperDestination
import com.madememagic.limelight.presentation.auth.AuthViewModel

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
                        SearchTopAppBar(
                            query = state.searchQuery,
                            onQueryChange = viewModel::onSearchQueryChange,
                            searchSuggestions = state.searchSuggestions,
                            onSuggestionClick = { note -> onNavigate(note) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HomeContent(
                            movies = state.filteredMovies,
                            onMovieClick = { onNavigate(it) },
                        )
                    }


                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    searchSuggestions: List<Movie>,
    onSuggestionClick: (Movie) -> Unit
) {
    var active by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(active) {
        if (!active) {
            // Clear search when closing the search bar
            onQueryChange("")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (active) 400.dp else 80.dp) // Fixed height when expanded
            .padding(horizontal = 16.dp)
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {
                active = false
                onQueryChange("")
            },
            active = active,
            onActiveChange = { newActive ->
                active = newActive
                if (!newActive) {
                    onQueryChange("")
                }
            },
            placeholder = { Text("Search your movies") },
        ) {
            if (searchSuggestions.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) // Fixed height for suggestions
                ) {
                    LazyColumn {
                        val searchList = searchSuggestions.take(4)
                        items(searchList.size) { index ->
                            val note = searchList[index]
                            ListItem(
                                headlineContent = { Text(note.title) },
                                supportingContent = {
                                    Text("Suggestion")
                                },
                                leadingContent = {
                                    Box(modifier = Modifier.size(16.dp)) {
                                        Icon(
                                            painterResource(id = R.drawable.icon_bulb),
                                            contentDescription = null
                                        )
                                    }
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .clickable {
                                        onSuggestionClick(note)
                                        active = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun HomeContent(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(56.dp))

        Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "Showing Movies")
    }
}

