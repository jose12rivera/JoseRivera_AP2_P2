package edu.ucne.joserivera_ap2_p2.data.remote

import edu.ucne.joserivera_ap2_p2.data.remote.dto.ContribuidoreDto
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") username: String
    ): Response<List<RepositoryDto>>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<ContribuidoreDto>>
}
