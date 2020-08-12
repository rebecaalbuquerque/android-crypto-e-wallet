package com.albuquerque.cryptoe_wallet.core.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

abstract class Remote: CoroutineScope by CoroutineScope(Dispatchers.IO) {

    companion object {

        val okHttpClient: OkHttpClient = let {
            val loggingLevel = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder().apply {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = loggingLevel

                addInterceptor(WalletInterceptor())
                addInterceptor(loggingInterceptor)
                addNetworkInterceptor(StethoInterceptor())

            }.build()

        }

    }

    fun getRetrofitBuilder(url: String): Retrofit {

        return Retrofit.Builder().apply {
            baseUrl(url)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()

    }

    fun <T> runRequest(request: T): Result<T> {

        try {

            return Result.success(request)

        } catch (e: Exception) {

            (e as? HttpException)?.response()?.errorBody()?.let { response ->
                return try {
                    val message = JSONObject(response.toString()).getString("status_message")
                    Result.failure(Error(Exception(message, e.cause)))
                } catch (e: Exception) {
                    Result.failure(Error(Exception("Desculpe, tivemos um problema, por favor tente novamente mais tarde.", e.cause)))
                }
            } ?: kotlin.run {
                return Result.failure(Error(e))
            }

        }

    }

}