package com.albuquerque.cryptoe_wallet.app.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.view.holder.CurrencyViewHolder
import com.albuquerque.cryptoe_wallet.core.adapter.BaseAdapter
import com.albuquerque.cryptoe_wallet.databinding.ItemWalletBinding
import com.albuquerque.cryptoe_wallet.databinding.ItemWalletBindingImpl

class CurrenciesAdapter(
    private val onClick: (typeTransaction: TypeTransaction, item: CryptocurrencyUI) -> Unit
): BaseAdapter<CryptocurrencyUI, CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return CurrencyViewHolder(
            ItemWalletBindingImpl(null, view) as ItemWalletBinding
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItemAt(position), onClick)
    }

}