package br.com.dfn.equities.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EquitiesResponse(
    @Json(name = "logo") val logo: String,
    @Json(name = "description") val description: String,
    @Json(name = "code") val code: String,
    @Json(name = "last_price") val lastPrice: String,
    @Json(name = "percent") val percent: String
)