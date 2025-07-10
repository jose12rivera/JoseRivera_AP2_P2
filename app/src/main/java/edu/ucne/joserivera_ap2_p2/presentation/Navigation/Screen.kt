package edu.ucne.joserivera_ap2_p2.presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()
    @Serializable
    data class ContributorList(val owner: String, val repoName: String) : Screen()
    @Serializable
    data class RepositoryList(val username: String) : Screen()

    @Serializable
    data class ViewRepository(
        val name: String,
        val desc: String? = null,
        val url: String
    ) : Screen()
}
