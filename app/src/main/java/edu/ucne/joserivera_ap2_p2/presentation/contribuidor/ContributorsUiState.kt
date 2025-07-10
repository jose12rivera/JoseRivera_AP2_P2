package edu.ucne.joserivera_ap2_p2.presentation.contribuidor

import edu.ucne.joserivera_ap2_p2.data.remote.dto.ContribuidoreDto

data class ContributorsUiState(
    val contributors: List<ContribuidoreDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)