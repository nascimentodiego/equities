package br.com.dfn.equities.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    fun doOnViewModelScope(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}