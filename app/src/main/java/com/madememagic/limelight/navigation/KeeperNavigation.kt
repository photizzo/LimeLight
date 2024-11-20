package com.madememagic.limelight.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.madememagic.limelight.presentation.auth.AuthScreen
import com.madememagic.limelight.presentation.auth.GenreScreen
import com.madememagic.limelight.presentation.home.HomeScreen
import com.madememagic.limelight.presentation.home.MainContainer
import com.madememagic.limelight.presentation.home.SettingsScreen

@Composable
fun KeeperNavigation(
    navController: NavHostController,
    startDestination: KeeperDestination,
    logout: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = when(navBackStackEntry?.destination?.route) {
        "auth" -> KeeperDestination.Auth
        "select_genre" -> KeeperDestination.SelectGenre
        "home" -> KeeperDestination.Home
        "manage_user_preference" -> KeeperDestination.ManageUserPreference
        else -> null
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<KeeperDestination.Auth> {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(KeeperDestination.SelectGenre) {
                        popUpTo(KeeperDestination.Auth) { inclusive = true }
                    }
                }
            )
        }

        composable<KeeperDestination.SelectGenre> {
            GenreScreen(
                onGenreSelected = {
                    navController.navigate(KeeperDestination.Home) {
                        popUpTo(KeeperDestination.SelectGenre) { inclusive = true }
                    }
                }
            )
        }

        composable<KeeperDestination.Home> {
            MainContainer(
                currentDestination = currentDestination,
                onNavigate = { movie ->
                    if (movie != null) {
                        navController.navigate(KeeperDestination.MovieDetails(movie.id))
                    }
                },
                onSettingsClick = {
                    navController.navigate(KeeperDestination.ManageUserPreference)
                },
                logout = logout
            )
        }


        composable<KeeperDestination.ManageUserPreference> {
            MainContainer(
                currentDestination = currentDestination,
                onNavigate = { movie ->
                    if (movie != null) {
                        navController.navigate(KeeperDestination.MovieDetails(movie.id))
                    }
                },
                onSettingsClick = {
                    navController.navigate(KeeperDestination.Home)
                },
                logout = logout
            )
        }

        composable<KeeperDestination.MovieDetails> { backStackEntry ->
            val movieDetails: KeeperDestination.MovieDetails = backStackEntry.toRoute()

        }

    }
}
