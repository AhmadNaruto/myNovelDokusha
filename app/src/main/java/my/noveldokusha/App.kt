package my.noveldokusha

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import my.noveldokusha.di.HiltAppEntryPoint
import my.noveldokusha.network.NetworkClient
import my.noveldokusha.network.ScraperNetworkClient
import my.noveldokusha.scraper.extension.manager.ExtensionManager
import my.noveldokusha.tooling.application_workers.setup.PeriodicWorkersInitializer
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var networkClient: NetworkClient

    @Inject
    lateinit var periodicWorkersInitializer: PeriodicWorkersInitializer

    @Inject
    lateinit var extensionManager: ExtensionManager

    // Application scope for background tasks
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

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
        
        // Load scraper extensions in background
        applicationScope.launch {
            extensionManager.loadExtensions()
        }
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
