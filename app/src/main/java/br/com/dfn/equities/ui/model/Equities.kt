package br.com.dfn.equities.ui.model

import br.com.dfn.equities.data.model.EquitiesResponse

data class Equities(
    val logo: String,
    val description: String,
    val code: String,
    val lastPrice: String,
    val percent: String
) {
    constructor(response: EquitiesResponse) : this(
        logo = response.logo,
        description = response.description,
        code = response.code,
        lastPrice = response.lastPrice,
        percent = response.percent
    )
}