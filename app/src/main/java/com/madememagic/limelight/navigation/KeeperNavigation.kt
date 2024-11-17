package com.madememagic.limelight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.madememagic.limelight.presentation.auth.AuthScreen
import com.madememagic.limelight.presentation.home.HomeScreen

@Composable
fun KeeperNavigation(
    navController: NavHostController,
    startDestination: KeeperDestination,
    logout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<KeeperDestination.Auth> {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(KeeperDestination.Home) {
                        popUpTo(KeeperDestination.Auth) { inclusive = true }
                    }
                }
            )
        }

        composable<KeeperDestination.Home> {
            HomeScreen(
                logout = logout,
                onNavigate = { note ->
                    if (note == null) {

                    } else {
                        navController.navigate(KeeperDestination.MovieDetails(note.id))
                    }
                }
            )
        }

        composable<KeeperDestination.MovieDetails> { backStackEntry ->
            val movieDetails: KeeperDestination.MovieDetails = backStackEntry.toRoute()

        }


        composable<KeeperDestination.ManageUserPreference> {

        }
    }
}
