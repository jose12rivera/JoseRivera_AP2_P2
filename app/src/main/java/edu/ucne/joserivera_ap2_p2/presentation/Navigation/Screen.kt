package edu.ucne.joserivera_ap2_p2.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()
    @Serializable
    data object RepositoryList : Screen()

    @Serializable
    data class RepositoryDetail(val id: Int) : Screen()

}