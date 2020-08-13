package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albuquerque.cryptoe_wallet.core.extensions.getCurrentBrazilianTime

@Entity(tableName = "transaction")
data class TransactionEntity(
    val sourceCurrency: String,
    val targetCurrency: String,
    val typeTransaction: Int,
    @PrimaryKey val date: Long = getCurrentBrazilianTime().time
)