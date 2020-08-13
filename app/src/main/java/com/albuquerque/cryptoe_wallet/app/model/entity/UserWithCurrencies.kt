package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class UserWithCurrencies(
    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "email", entityColumn = "name", associateBy = Junction(UserCurrency::class))
    val currencies: List<CryptocurrencyEntity>
)