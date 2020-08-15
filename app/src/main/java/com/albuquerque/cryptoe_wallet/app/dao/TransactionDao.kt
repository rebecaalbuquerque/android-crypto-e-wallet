package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.*
import com.albuquerque.cryptoe_wallet.app.model.entity.*
import com.albuquerque.cryptoe_wallet.app.utils.Session
import com.albuquerque.cryptoe_wallet.core.database.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao: BaseDao<TransactionEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWithTransaction(join: UserTransaction)

    @Transaction
    @Query("select * from user WHERE email=:user")
    fun getUserWithTransactions(user: String = Session.userLogged): Flow<List<UserWithTransactions>>

}