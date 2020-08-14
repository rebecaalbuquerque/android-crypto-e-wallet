package com.albuquerque.cryptoe_wallet.app.bindingAdapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCurrency
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*

@SuppressLint("SetTextI18n")
@BindingAdapter("app:typeTransaction", "app:currency")
fun setTransactionCurrencyInformation(
    textView: TextView,
    typeTransaction: TypeTransaction?,
    currency: CryptocurrencyUI?
) {
    typeTransaction?.let {
        when (typeTransaction) {
            SALE -> {
                currency?.let {
                    textView.text = "Uma unidade de ${currency.name} renderá ${currency.sellValue.toBrazilianCurrency()}"
                }

            }

            PURCHASE, EXCHANGE -> {
                currency?.let {
                    textView.text = "Uma unidade de ${currency.name} custa ${currency.buyValue.toBrazilianCurrency()}"
                }
            }
        }
    }
}

@BindingAdapter("app:transactionName", "app:sourceCurrency", "app:targetCurrency")
fun setTransactionName(textView: TextView, transactionId: Int, sourceCurrency: String, targetCurrency: String) {

    val result = when (val typeTransaction = TypeTransaction.getByValue(transactionId)) {
        SALE -> R.drawable.ic_compra to "${typeTransaction.title} de $sourceCurrency"
        PURCHASE -> R.drawable.ic_venda to "${typeTransaction.title} de $sourceCurrency"
        EXCHANGE -> R.drawable.ic_troca to "${typeTransaction.title} de $sourceCurrency por $targetCurrency"
    }

    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(result.first, 0, 0, 0)
    textView.text = result.second

}