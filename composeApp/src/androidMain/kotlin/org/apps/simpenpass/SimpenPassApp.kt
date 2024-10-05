package org.apps.simpenpass

import android.app.Application
import org.apps.simpenpass.di.appModules
import org.apps.simpenpass.di.ktorModules
import org.apps.simpenpass.di.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class SimpenPassApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SimpenPassApp)
            modules(
                appModules, ktorModules,repoModule
            )

        }
    }
}