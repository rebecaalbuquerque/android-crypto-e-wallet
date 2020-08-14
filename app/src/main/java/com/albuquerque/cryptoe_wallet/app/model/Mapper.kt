package com.albuquerque.cryptoe_wallet.app.model

import com.albuquerque.cryptoe_wallet.app.model.dto.BitcoinDTO
import com.albuquerque.cryptoe_wallet.app.model.dto.BritaDTO
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.TransactionEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.model.ui.TransactionUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency

fun BritaDTO.toCryptocurrencyDTO(): CryptocurrencyDTO {
    return CryptocurrencyDTO(
        TypeCryptocurrency.BRITA.value,
        this.value.lastOrNull { it.tipoBoletim == "Fechamento PTAX" || it.tipoBoletim == "Abertura" || it.tipoBoletim == "Intermediário" }?.cotacaoCompra,
        this.value.lastOrNull { it.tipoBoletim == "Fechamento PTAX" || it.tipoBoletim == "Abertura" || it.tipoBoletim == "Intermediário" }?.cotacaoVenda
    )
}

fun BitcoinDTO.toCryptocurrencyDTO(): CryptocurrencyDTO {
    return CryptocurrencyDTO(
        TypeCryptocurrency.BITCOIN.value,
        this.ticker.buy,
        this.ticker.sell
    )
}

fun UserEntity.toUI(): UserUI {
    return UserUI(
        this.email,
        this.password,
        this.fullName,
        this.balance
    )
}

fun UserUI.toEntity(): UserEntity {
    return UserEntity(
        this.email,
        this.password,
        this.fullName,
        this.balance
    )
}

fun CryptocurrencyEntity.toUI(): CryptocurrencyUI {
    return CryptocurrencyUI(
        this.name,
        this.buy,
        this.sell
    )
}

fun CryptocurrencyUI.toEntity(user: String): UserCryptocurrencyEntity {
    return UserCryptocurrencyEntity(
        this.name,
        user,
        this.buyValue,
        this.sellValue,
        this.amount
    )
}

fun UserCryptocurrencyEntity.toUI(): CryptocurrencyUI {
    return CryptocurrencyUI(
        this.currency,
        this.buy,
        this.sell,
        this.amount
    )
}

fun CryptocurrencyDTO.toEntity(): CryptocurrencyEntity {
    return CryptocurrencyEntity(
        this.name,
        this.buyValue!!,
        this.sellValue!!
    )
}

fun TransactionEntity.toUI(): TransactionUI {
    return TransactionUI(
        this.sourceCurrency,
        this.targetCurrency,
        this.typeTransaction,
        this.amount,
        this.date
    )
}