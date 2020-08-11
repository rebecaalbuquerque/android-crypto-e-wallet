package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency.*


/**
 * @see RemoteRepositoryImpl
 * @see LocalRepositoryImpl
 * */

class RepositoryImpl(
    private val remote: RemoteRepository,
    private val local: LocalRepository
) : Repository {

    override fun hasLoggedUser(): LiveData<Boolean> = local.hasLoggedUser()

    override suspend fun signUp(user: UserEntity) {
        local.signUp(user)
    }

    override suspend fun signIn(email: String, password: String): Result<UserEntity> {
        val user = local.signIn(email, password)

        return if (user != null) {
            local.saveSession(user.email)
            Result.success(user)
        } else {
            Result.failure(Throwable("Verifique as credenciais informadas."))
        }

    }

    override suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO> {
        return when (typeCriptocurrency) {
            BITCOIN -> remote.fetchBicoinInfo()
                .onSuccess { bitcoin ->
                    local.saveCurrency(bitcoin.toEntity())
                }

            BRITA -> remote.fetchBritaInfo()
                .onSuccess { brita ->
                    local.saveCurrency(brita.toEntity())
                }
        }
    }

}