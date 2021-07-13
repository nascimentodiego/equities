package br.com.dfn.equities.di

import br.com.dfn.equities.repositories.equities.EquitiesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { EquitiesRepository(get()) }
}