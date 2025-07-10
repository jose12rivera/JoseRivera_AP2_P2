package edu.ucne.joserivera_ap2_p2.presentation.respository

import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto

data class RepositoryUiState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryDto> = emptyList(),
    val name: String? = null,
    val description: String? = null,
    val htmlUrl: String? = null,
    val errorMessage: String? = null,
    val inputError: String? = null
)