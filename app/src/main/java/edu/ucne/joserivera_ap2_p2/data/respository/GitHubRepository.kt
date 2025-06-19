package edu.ucne.joserivera_ap2_p2.data.respository


import edu.ucne.joserivera_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.joserivera_ap2_p2.data.remote.Resource
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getRepositories(username: String): Flow<Resource<List<RepositoryDto>>> = flow {
        emit(Resource.Loading())
        try {
            val repositories = remoteDataSource.getRepositories(username)
            emit(Resource.Success(repositories))
        } catch (e: Exception) {
            emit(Resource.Error("Error al obtener repositorios: ${e.message}"))
        }
    }
}