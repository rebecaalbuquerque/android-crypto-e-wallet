package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity

@Entity(primaryKeys = ["email", "name"])
data class UserCurrency(
    val email: String,
    val name: String,
    val amount: Int
)