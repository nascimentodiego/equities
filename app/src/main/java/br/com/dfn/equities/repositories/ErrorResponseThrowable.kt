package br.com.dfn.equities.repositories

import com.squareup.moshi.Json
import okhttp3.ResponseBody
import retrofit2.HttpException
import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi


data class ErrorResponseThrowable(
    @Json(name = "code") val code: String? = null,
    @Json(name = "message") override val message: String? = null,
    @Json(name = "short_message") val shortMessage: String? = null,
    private val throwable: Throwable? = null
) : Throwable(message, throwable) {
    companion object {
        private fun fromErrorBody(errorBody: ResponseBody?) = try {
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<ErrorResponseThrowable> =
                moshi.adapter(ErrorResponseThrowable::class.java)
            jsonAdapter.fromJson(errorBody?.string())
        } catch (e: Exception) {
            null
        }

        fun fromException(t: HttpException) = fromErrorBody(t.response()?.errorBody())
    }
}