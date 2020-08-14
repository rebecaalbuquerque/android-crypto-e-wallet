package com.albuquerque.cryptoe_wallet.app.view.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.adapter.CurrenciesAdapter
import com.albuquerque.cryptoe_wallet.app.viewmodel.WalletViewModel
import com.albuquerque.cryptoe_wallet.core.view.fragment.BaseFragment
import com.albuquerque.cryptoe_wallet.databinding.FragmentWalletBinding
import kotlinx.android.synthetic.main.fragment_wallet.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class WalletFragment : BaseFragment() {

    private lateinit var currenciesAdapter: CurrenciesAdapter
    private lateinit var binding: FragmentWalletBinding
    private val walletViewModel: WalletViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupDataBinding()

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.title = ""
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@WalletFragment
            viewModel = walletViewModel
            executePendingBindings()
        }
    }

    private fun setupUI() {

        currenciesAdapter = CurrenciesAdapter { typeTransaction, item ->
            findNavController().navigate(
                WalletFragmentDirections.nextAction(typeTransaction.id, item.name)
            )
        }

        recyclerViewWallet.adapter = currenciesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_wallet, menu)
    }

}