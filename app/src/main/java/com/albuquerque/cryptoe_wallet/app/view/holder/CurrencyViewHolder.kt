package com.albuquerque.cryptoe_wallet.app.view.holder

import androidx.databinding.ViewDataBinding
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*
import com.albuquerque.cryptoe_wallet.core.holder.BaseViewHolder
import com.albuquerque.cryptoe_wallet.databinding.ItemWalletBinding

class CurrencyViewHolder(binding: ViewDataBinding): BaseViewHolder<CryptocurrencyUI>(binding) {

    private lateinit var onClick: (typeTransaction: TypeTransaction, item: CryptocurrencyUI) -> Unit

    override fun bind(item: CryptocurrencyUI) {
        with(binding as ItemWalletBinding) {
            currency = item

            buttonSell.setOnClickListener { onClick(SALE, item) }

            buttonBuy.setOnClickListener { onClick(PURCHASE, item) }

            buttonExchange.setOnClickListener { onClick(EXCHANGE, item) }

        }
    }

    fun bind(item: CryptocurrencyUI, onClick: (typeTransaction: TypeTransaction, item: CryptocurrencyUI) -> Unit) {
        this.onClick = onClick
        bind(item)
    }

}