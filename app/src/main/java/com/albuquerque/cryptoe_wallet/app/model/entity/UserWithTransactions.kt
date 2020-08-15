package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class UserWithTransactions(
    @Embedded
    val user: UserEntity,

    @Relation(parentColumn = "email", entityColumn = "date", associateBy = Junction(UserTransaction::class))
    val transactions: List<TransactionEntity>
)