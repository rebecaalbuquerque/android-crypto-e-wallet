package com.albuquerque.cryptoe_wallet.app.model.ui

import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.extensions.format
import java.math.BigDecimal

data class TransactionUI(
    val sourceCurrency: String,
    val targetCurrency: String,
    val typeTransaction: Int,
    val amount: BigDecimal,
    val date: Long
) {
    val nameTransaction: String = TypeTransaction.getByValue(typeTransaction).title
    val dateFormatted: String = date.format("dd 'de' MMMM 'de' yyyy 'às' HH:mm")
}