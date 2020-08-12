package com.albuquerque.cryptoe_wallet.core.remote

import okhttp3.Interceptor
import okhttp3.Response

class WalletInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .build()

        return chain.proceed(request)
    }
}