package edu.ucne.joserivera_ap2_p2.data.remote

import edu.ucne.joserivera_ap2_p2.data.remote.dto.ContribuidoreDto
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubApi: GitHubApi
) {
    suspend fun getRepositories(username: String): List<RepositoryDto> {
        val response = gitHubApi.getRepositories(username)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }

    suspend fun getContributors(owner: String, repo: String): List<ContribuidoreDto> {
        val response = gitHubApi.getContributors(owner, repo)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}
