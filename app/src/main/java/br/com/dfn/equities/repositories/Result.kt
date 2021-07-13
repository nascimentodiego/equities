package br.com.dfn.equities.repositories

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    class Success<out T>(val data: T) : Result<T>()
    class EquitiesError(val error: ErrorResponseThrowable?) : Result<Nothing>()
    object NoConnectionError : Result<Nothing>()
    object GenericError : Result<Nothing>()
}