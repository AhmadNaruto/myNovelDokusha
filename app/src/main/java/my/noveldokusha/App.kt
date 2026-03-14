package my.noveldokusha

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import my.noveldokusha.di.HiltAppEntryPoint
import my.noveldokusha.network.NetworkClient
import my.noveldokusha.network.ScraperNetworkClient
import my.noveldokusha.tooling.application_workers.setup.PeriodicWorkersInitializer
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var networkClient: NetworkClient

    @Inject
    lateinit var periodicWorkersInitializer: PeriodicWorkersInitializer

    // Coil 3.x ImageLoader - provided via Hilt now
    val imageLoader: ImageLoader by lazy {
        createImageLoader(this)
    }

    private fun createImageLoader(context: PlatformContext): ImageLoader = when (val networkClient = networkClient) {
        is ScraperNetworkClient -> ImageLoader
            .Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(networkClient.client))
            }
            .build()

        else -> ImageLoader.Builder(context).build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        periodicWorkersInitializer.init()
    }

    // WorkManager
    override val workManagerConfiguration: Configuration by lazy {
        val appWorkerFactory = EntryPoints
            .get(this, HiltAppEntryPoint::class.java)
            .workerFactory()

        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.INFO)
            .setWorkerFactory(appWorkerFactory)
            .build()
    }
}
