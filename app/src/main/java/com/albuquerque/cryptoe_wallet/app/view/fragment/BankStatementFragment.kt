package com.albuquerque.cryptoe_wallet.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.adapter.TransactionsAdapter
import com.albuquerque.cryptoe_wallet.app.viewmodel.BankStatementViewModel
import com.albuquerque.cryptoe_wallet.core.view.fragment.BaseFragment
import com.albuquerque.cryptoe_wallet.databinding.FragmentBankStatementBinding
import kotlinx.android.synthetic.main.fragment_bank_statement.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class BankStatementFragment : BaseFragment() {

    private lateinit var binding: FragmentBankStatementBinding
    private val bankStatementViewModel: BankStatementViewModel by viewModel()
    private val transactionsAdapter = TransactionsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_statement, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
        setupDataBinding()
    }

    private fun setupView() {
        recyclerViewTransaction.adapter = transactionsAdapter
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@BankStatementFragment
            viewModel = bankStatementViewModel
            executePendingBindings()
        }
    }

}