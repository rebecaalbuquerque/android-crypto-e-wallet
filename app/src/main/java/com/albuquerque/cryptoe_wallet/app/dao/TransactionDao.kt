package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albuquerque.cryptoe_wallet.app.model.entity.TransactionEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCurrency
import com.albuquerque.cryptoe_wallet.app.model.entity.UserTransaction
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface TransactionDao: BaseDao<TransactionEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWithTransaction(join: UserTransaction)

    @Query("DELETE FROM session")
    suspend fun deleteAll()

}