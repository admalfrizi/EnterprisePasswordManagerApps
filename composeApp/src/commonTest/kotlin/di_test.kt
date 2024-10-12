import api_testing.ApiMocking
import org.apps.simpenpass.data.source.localData.LocalStoreData
import org.apps.simpenpass.di.platformModule
import org.apps.simpenpass.di.remoteDataModule
import org.apps.simpenpass.di.repoModule
import org.apps.simpenpass.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun startApp() {
    startKoin {
        modules(platformModule(),testModule, viewModelModule,repoModule, remoteDataModule)
    }
}

fun stopApp() {
    stopKoin()
}

val testModule = module {
    single {
        ApiMocking().client
    }
    single {
        LocalStoreData(
            dataStore = get()
        )
    }
}
