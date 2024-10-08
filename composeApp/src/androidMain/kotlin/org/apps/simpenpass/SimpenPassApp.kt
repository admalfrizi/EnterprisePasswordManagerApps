package org.apps.simpenpass

import android.app.Application
import org.apps.simpenpass.di.initKoin
import org.apps.simpenpass.di.ktorModules
import org.apps.simpenpass.di.remoteDataModule
import org.apps.simpenpass.di.repoModule
import org.apps.simpenpass.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SimpenPassApp: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SimpenPassApp)
        }
    }
}