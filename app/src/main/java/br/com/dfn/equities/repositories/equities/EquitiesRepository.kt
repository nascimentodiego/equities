package br.com.dfn.equities.repositories.equities

import br.com.dfn.equities.data.remote.api.EquitiesApi
import br.com.dfn.equities.repositories.requestApi

class EquitiesRepository(private val api: EquitiesApi) {
    suspend fun getEquities() = requestApi {
        api.getEquities()
    }
}