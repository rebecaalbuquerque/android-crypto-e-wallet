package com.albuquerque.cryptoe_wallet.core.utils

object ResultUtils {

    fun <T> defaultFailureCurrencies(): Result<T> {
        return Result.failure(defaultThrowableCurrencies)
    }

    val defaultThrowableCurrencies = Throwable("Não foi possível recuperar as informações das criptomoedas. Tente novamente mais tarde.")

}