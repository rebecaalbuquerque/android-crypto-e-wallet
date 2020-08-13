package com.albuquerque.cryptoe_wallet.app.bindingAdapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.cryptoe_wallet.core.adapter.BaseAdapter
import com.albuquerque.cryptoe_wallet.core.holder.BaseViewHolder

@Suppress("UNCHECKED_CAST")
@BindingAdapter("app:items")
fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
    items?.let {
        (recyclerView.adapter as? BaseAdapter<T, BaseViewHolder<T>>)?.refresh(items)
    }
}