package edu.ucne.joserivera_ap2_p2.presentation.respository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.joserivera_ap2_p2.data.remote.Resource
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import edu.ucne.joserivera_ap2_p2.data.respository.GitHubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchRepositories("jose12rivera") // Puedes cambiar el username si lo deseas
    }

    fun fetchRepositories(username: String) {
        viewModelScope.launch {
            repository.getRepositories(username).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true, errorMessage = null)
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                repositories = result.data ?: emptyList(),
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message ?: "Error desconocido"
                            )
                        }
                    }
                }
            }
        }
    }

    fun validarCampos(): Boolean {
        val name = _uiState.value.name
        val url = _uiState.value.htmlUrl

        return when {
            name.isNullOrBlank() -> {
                _uiState.update { it.copy(inputError = "El nombre no puede estar vacío") }
                false
            }
            url.isNullOrBlank() -> {
                _uiState.update { it.copy(inputError = "El URL no puede estar vacío") }
                false
            }
            else -> {
                _uiState.update { it.copy(inputError = null) }
                true
            }
        }
    }

    fun setName(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun setDescription(value: String?) {
        _uiState.update { it.copy(description = value) }
    }

    fun setHtmlUrl(value: String) {
        _uiState.update { it.copy(htmlUrl = value) }
    }

    fun limpiarCampos() {
        _uiState.update {
            it.copy(
                name = null,
                description = null,
                htmlUrl = null,
                inputError = null
            )
        }
    }
}
