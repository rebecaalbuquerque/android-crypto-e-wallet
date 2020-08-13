package com.albuquerque.cryptoe_wallet.app.utils

enum class TypeTransaction(val id: Int, val title: String) {
    SALE(0, "Venda"), PURCHASE(1, "Compra"), EXCHANGE(2, "Troca");

    companion object {
        fun getByValue(id: Int): TypeTransaction {
            return values().find { it.id == id } ?: SALE
        }
    }

}