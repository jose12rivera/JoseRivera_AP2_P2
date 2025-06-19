package edu.ucne.joserivera_ap2_p2.data.respository

import edu.ucne.joserivera_ap2_p2.data.remote.GitHubApi
import edu.ucne.joserivera_ap2_p2.data.remote.Resource
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val api: GitHubApi
) {
    fun getRepositories(username: String): Flow<Resource<List<RepositoryDto>>> = flow {
        emit(Resource.Loading())
        try {
            val result = api.getRepositories(username)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error al obtener los repositorios"))
        }
    }
}
