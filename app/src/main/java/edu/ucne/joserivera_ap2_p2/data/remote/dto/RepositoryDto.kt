package edu.ucne.joserivera_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class RepositoryDto(
    val id: Int? = null,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val htmlUrl: String,
    @Transient var isLocal: Boolean = false // Ignorar en la serialización
)
