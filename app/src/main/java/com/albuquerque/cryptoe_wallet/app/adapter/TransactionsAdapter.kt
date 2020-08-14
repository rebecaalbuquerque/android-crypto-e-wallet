package com.albuquerque.cryptoe_wallet.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.model.ui.TransactionUI
import com.albuquerque.cryptoe_wallet.app.view.holder.TransactionViewHolder
import com.albuquerque.cryptoe_wallet.core.adapter.BaseAdapter
import com.albuquerque.cryptoe_wallet.databinding.ItemTransactionBinding
import com.albuquerque.cryptoe_wallet.databinding.ItemTransactionBindingImpl

class TransactionsAdapter: BaseAdapter<TransactionUI, TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(
            ItemTransactionBindingImpl(null, view) as ItemTransactionBinding
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItemAt(position))
    }

}