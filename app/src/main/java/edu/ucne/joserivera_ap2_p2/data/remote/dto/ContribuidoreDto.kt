package edu.ucne.joserivera_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class ContribuidoreDto (
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "contributions") val contributions: Int
)