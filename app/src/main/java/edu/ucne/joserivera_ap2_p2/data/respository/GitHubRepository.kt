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
    fun getRepositories(username: String): Flow<Resource<List<RepositoryDto>>> = flow {
        try {
            emit(Resource.Loading())
            val repos = remoteDataSource.getRepositories(username)
            emit(Resource.Success(repos))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}
