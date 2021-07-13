package br.com.dfn.equities.di

import br.com.dfn.equities.ui.home.EquitiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModules = module {
    viewModel { EquitiesViewModel(get()) }
}