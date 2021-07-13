package br.com.dfn.equities

import android.app.Application
import br.com.dfn.equities.data.remote.api.networkModule
import br.com.dfn.equities.di.repositoryModule
import br.com.dfn.equities.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class EquitiesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@EquitiesApplication)
            modules(
                networkModule,
                repositoryModule,
                viewModelModules
            )
        }
    }
}