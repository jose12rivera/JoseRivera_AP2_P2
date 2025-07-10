package edu.ucne.joserivera_ap2_p2.presentation.contribuidor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joserivera_ap2_p2.data.remote.Resource
import edu.ucne.joserivera_ap2_p2.data.respository.ContribuidorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContribuidorViewModel @Inject constructor(
    private val repository: ContribuidorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContributorsUiState())
    val uiState: StateFlow<ContributorsUiState> = _uiState.asStateFlow()

    fun getContributors(owner: String, repo: String) {
        viewModelScope.launch {
            repository.getContributors(owner, repo).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            contributors = resource.data ?: emptyList(),
                            errorMessage = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "Error desconocido"
                        )
                    }
                }
            }
        }
    }
}
