package com.albuquerque.cryptoe_wallet.app.model

import com.albuquerque.cryptoe_wallet.app.model.dto.BitcoinDTO
import com.albuquerque.cryptoe_wallet.app.model.dto.BritaDTO
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import java.math.BigDecimal


fun UserEntity.toUI(): UserUI {
    return UserUI(
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

fun CryptocurrencyDTO.toEntity(): CryptocurrencyEntity {
    return CryptocurrencyEntity(
        this.name,
        this.buyValue,
        this.sellValue,
        BigDecimal(0)
    )
}

fun BritaDTO.toCryptocurrencyDTO(): CryptocurrencyDTO {
    return CryptocurrencyDTO(
        "Brita",
        this.value.last { it.tipoBoletim == "Fechamento PTAX" }.cotacaoCompra,
        this.value.last { it.tipoBoletim == "Fechamento PTAX" }.cotacaoVenda
    )
}

fun BitcoinDTO.toCryptocurrencyDTO(): CryptocurrencyDTO {
    return CryptocurrencyDTO(
        "Bitcoin",
        this.ticker.buy,
        this.ticker.sell
    )
}