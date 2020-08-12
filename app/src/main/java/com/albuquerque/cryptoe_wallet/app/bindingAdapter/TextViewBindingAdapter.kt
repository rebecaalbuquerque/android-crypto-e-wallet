package com.albuquerque.cryptoe_wallet.app.bindingAdapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*

@SuppressLint("SetTextI18n")
@BindingAdapter("app:typeTransaction", "app:sourceCurrency", "app:targetCurrency", requireAll = false)
fun setTransactionCurrencyInformation(
    textView: TextView,
    typeTransaction: TypeTransaction?,
    sourceCurrency: CryptocurrencyUI?,
    targetCurrency: CryptocurrencyUI? = null
) {
    typeTransaction?.let {
        when (typeTransaction) {
            SALE -> {
                sourceCurrency?.let {
                    textView.text = "Uma unidade de ${sourceCurrency.name} renderá ${sourceCurrency.sellValue.toBrazilianCurrency()}"
                }

            }

            PURCHASE -> {
                sourceCurrency?.let {
                    textView.text = "Uma unidade de ${sourceCurrency.name} custa ${sourceCurrency.buyValue.toBrazilianCurrency()}"
                }
            }

            EXCHANGE -> {
                sourceCurrency?.let {
                    targetCurrency?.let {
                        textView.text = "Uma unidade de ${sourceCurrency.name} custa ${sourceCurrency.buyValue.toBrazilianCurrency()}\n" +
                                "\"Uma unidade de ${targetCurrency.name} custa ${targetCurrency.buyValue.toBrazilianCurrency()}"
                    }
                }

            }
        }
    }
}