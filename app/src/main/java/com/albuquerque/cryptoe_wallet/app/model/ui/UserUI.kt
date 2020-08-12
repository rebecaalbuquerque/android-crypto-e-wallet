package com.albuquerque.cryptoe_wallet.app.model.ui

import java.math.BigDecimal

data class UserUI(
    val email: String,
    val password: String,
    val fullName: String,
    val balance: BigDecimal
) {
    val firstName = fullName.split(" ").first()
}