package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.*
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCurrency
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Transaction
    suspend fun checkUserAndDoLogin(email: String, password: String, onNext: suspend (user: UserEntity?) -> Unit): UserEntity? {
        val user = getUserByEmailAndPassword(email, password)
        onNext.invoke(user)
        return user
    }

}