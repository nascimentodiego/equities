package br.com.dfn.equities.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

suspend fun <T> requestApi(api: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(api.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is UnknownHostException,
                is SocketException,
                is IOException -> Result.NoConnectionError
                is HttpException -> {
                    val errorResponse = ErrorResponseThrowable.fromException(throwable)
                    Result.EquitiesError(errorResponse)
                }
                else -> {
                    Result.GenericError
                }
            }
        }
    }
}