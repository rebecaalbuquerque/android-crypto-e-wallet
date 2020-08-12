package com.albuquerque.cryptoe_wallet.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.albuquerque.cryptoe_wallet.app.model.entity.SessionEntity
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface SessionDao: BaseDao<SessionEntity> {

    @Query("SELECT COUNT(*) FROM session")
    fun hasUserLogged(): LiveData<Int>

    @Query("DELETE FROM session")
    suspend fun deleteAll()
}