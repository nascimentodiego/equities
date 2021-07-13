package br.com.dfn.equities.data.remote.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val REQUEST_TIMEOUT = 10L

val networkModule = module {

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    single {
        val moshi = Moshi.Builder().build()

        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("http://10.0.0.178:3001/")
            .client(get())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(EquitiesApi::class.java)
    }
}