package com.albuquerque.cryptoe_wallet.app.view.holder

import androidx.databinding.ViewDataBinding
import com.albuquerque.cryptoe_wallet.app.model.ui.TransactionUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.holder.BaseViewHolder
import com.albuquerque.cryptoe_wallet.databinding.ItemTransactionBinding

class TransactionViewHolder(binding: ViewDataBinding): BaseViewHolder<TransactionUI>(binding) {

    override fun bind(item: TransactionUI) {
        with(binding as ItemTransactionBinding) {
            transaction = item
        }

    }
}