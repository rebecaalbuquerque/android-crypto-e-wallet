package com.albuquerque.cryptoe_wallet.app.utils

enum class TypeTransaction(val id: Int) {
    SALE(0), PURCHASE(1), EXCHANGE(2);

    companion object {
        fun getByValue(id: Int): TypeTransaction {
            return values().find { it.id == id } ?: SALE
        }
    }

}