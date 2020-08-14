package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import java.math.BigDecimal

/**
 * @param email is the id of the User
 * @param name is the id of the Currency
 * */
@Entity(primaryKeys = ["email", "name"])
data class UserCurrency(
    val email: String,
    val name: String,
    val amount: BigDecimal
)