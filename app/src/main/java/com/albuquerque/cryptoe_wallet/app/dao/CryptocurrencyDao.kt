package com.albuquerque.cryptoe_wallet.app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.utils.Session
import com.albuquerque.cryptoe_wallet.core.database.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptocurrencyDao: BaseDao<CryptocurrencyEntity> {

    @Query("select * from cryptocurrency")
    fun getAll(): List<CryptocurrencyEntity>

    @Query("select * from user_cryptocurrency WHERE currency=:name AND user=:user")
    fun getByNameAsLiveData(name: String, user: String = Session.userLogged): LiveData<UserCryptocurrencyEntity?>

    @Transaction
    suspend fun getCurrenciesAndInsertWithUser(user: UserEntity, action: suspend () -> Unit): UserEntity {
        action.invoke()
        return user
    }

    @Transaction
    @Query("select * from user_cryptocurrency WHERE user=:user ORDER BY currency")
    fun getUserCurrencies(user: String = Session.userLogged): Flow<List<UserCryptocurrencyEntity>>

}