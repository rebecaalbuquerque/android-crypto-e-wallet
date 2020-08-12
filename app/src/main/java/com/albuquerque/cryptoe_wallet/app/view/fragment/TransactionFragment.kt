package com.albuquerque.cryptoe_wallet.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.viewmodel.TransactionViewModel
import com.albuquerque.cryptoe_wallet.core.view.fragment.BaseFragment
import com.albuquerque.cryptoe_wallet.databinding.FragmentTransactionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TransactionFragment : BaseFragment() {

    private val safeArgs: TransactionFragmentArgs by navArgs()
    private val transactionViewModel: TransactionViewModel by viewModel()
    private lateinit var binding: FragmentTransactionBinding
    private lateinit var typeTransaction: TypeTransaction

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        typeTransaction = TypeTransaction.getByValue(safeArgs.typeTransaction)
        setupDataBinding()

        transactionViewModel.typeTransaction = typeTransaction
        transactionViewModel.nameCurrency = safeArgs.nameCurrency
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.title = typeTransaction.title
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@TransactionFragment
            viewModel = transactionViewModel
            executePendingBindings()
        }
    }

}