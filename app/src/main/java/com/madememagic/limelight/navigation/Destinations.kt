package com.madememagic.limelight.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class KeeperDestination {
    @Serializable
    data object Auth: KeeperDestination()
    @Serializable
    data object Home: KeeperDestination()
    @Serializable
    data object ManageUserPreference: KeeperDestination()

    @Serializable
    data class MovieDetails(val movieId: String): KeeperDestination()
}

