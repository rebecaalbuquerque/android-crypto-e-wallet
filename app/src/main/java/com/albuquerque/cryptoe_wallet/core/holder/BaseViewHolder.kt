package com.albuquerque.cryptoe_wallet.core.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom class that simplify the use a an ViewHolder for DataBinding usage.
 * */
abstract class BaseViewHolder<T>(var binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root), IBindView<T> {

    abstract override fun bind(item: T)

}