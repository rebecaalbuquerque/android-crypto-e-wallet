package com.albuquerque.cryptoe_wallet.core.remote

import com.albuquerque.cryptoe_wallet.core.extensions.add
import com.albuquerque.cryptoe_wallet.core.extensions.getCurrentBrazilianTime
import com.albuquerque.cryptoe_wallet.core.extensions.minus
import java.util.*

object BritaApiHelper {

    private var amountDays = 0

    var isRequestSucessful: Boolean = false

    val lastDateRequested: Date
        get() {
            if(!isRequestSucessful)
                amountDays++

            return getCurrentBrazilianTime()
                .add(Calendar.DATE, 1)
                .minus(Calendar.DATE, amountDays)
        }

}