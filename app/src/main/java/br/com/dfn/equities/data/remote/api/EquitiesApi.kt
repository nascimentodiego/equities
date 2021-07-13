package br.com.dfn.equities.data.remote.api

import br.com.dfn.equities.data.model.EquitiesResponse
import retrofit2.http.GET

interface EquitiesApi {
    @GET("equities")
    suspend fun getEquities(): List<EquitiesResponse>
}