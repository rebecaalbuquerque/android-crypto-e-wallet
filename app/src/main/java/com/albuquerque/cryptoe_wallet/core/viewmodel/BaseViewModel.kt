package com.albuquerque.cryptoe_wallet.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    val onError = MutableLiveData<String>()
    val onSnackbarError = MutableLiveData<String>()
    val onStartLoading = MutableLiveData<Any>()
    val onFinishLoading = MutableLiveData<Any>()

}