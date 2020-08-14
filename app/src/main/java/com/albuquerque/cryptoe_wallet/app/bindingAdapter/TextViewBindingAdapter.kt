package com.albuquerque.cryptoe_wallet.app.bindingAdapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
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