package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "currencyId"])
data class UserCurrency(
    val userId: String,
    val currencyId: String,
    val amount: Int
)