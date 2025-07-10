package edu.ucne.joserivera_ap2_p2.data.respository

import edu.ucne.joserivera_ap2_p2.data.remote.RemoteDataSource
import edu.ucne.joserivera_ap2_p2.data.remote.Resource
import edu.ucne.joserivera_ap2_p2.data.remote.dto.ContribuidoreDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContribuidorRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getContributors(owner: String, repo: String): Flow<Resource<List<ContribuidoreDto>>> = flow {
        try {
            emit(Resource.Loading())
            val contributors = remoteDataSource.getContributors(owner, repo)
            emit(Resource.Success(contributors))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }
}
