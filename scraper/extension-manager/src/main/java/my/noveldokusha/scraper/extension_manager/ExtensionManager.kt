package my.noveldokusha.scraper.extension.manager

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import my.noveldokusha.scraper.api.Scraper
import my.noveldokusha.scraper.api.ScraperFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages loading and accessing scraper extensions.
 * 
 * Extensions are loaded dynamically from installed packages that:
 * 1. Have package name starting with "my.noveldokusha.scraper.provider."
 * 2. Contain a class named [ScraperFactoryImpl] implementing [ScraperFactory]
 */
@Singleton
class ExtensionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "ExtensionManager"
        private const val EXTENSION_PACKAGE_PREFIX = "my.noveldokusha.scraper.provider."
        private const val FACTORY_CLASS_NAME = "ScraperFactoryImpl"
    }

    private val scrapers = mutableMapOf<String, Scraper>()
    private val extensionPackages = mutableMapOf<String, String>() // scraperId -> packageName

    /**
     * Get all loaded scrapers
     */
    fun getAllScrapers(): List<Scraper> = scrapers.values.toList()

    /**
     * Get scraper by ID
     */
    fun getScraper(id: String): Scraper? = scrapers[id]

    /**
     * Check if a scraper is available
     */
    fun hasScraper(id: String): Boolean = scrapers.containsKey(id)

    /**
     * Load all installed extensions
     * Should be called once during app initialization
     */
    suspend fun loadExtensions(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val packageManager = context.packageManager
            val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)

            val extensionPackages = installedPackages.filter { pkg ->
                pkg.packageName.startsWith(EXTENSION_PACKAGE_PREFIX)
            }

            Log.d(TAG, "Found ${extensionPackages.size} extension packages")

            extensionPackages.forEach { pkg ->
                try {
                    loadExtension(pkg.packageName, packageManager)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load extension: ${pkg.packageName}", e)
                }
            }

            Log.d(TAG, "Loaded ${scrapers.size} scrapers: ${scrapers.keys.joinToString()}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading extensions", e)
            Result.failure(e)
        }
    }

    /**
     * Load a single extension from package name
     */
    @Suppress("UNCHECKED_CAST")
    private fun loadExtension(packageName: String, packageManager: PackageManager) {
        Log.d(TAG, "Loading extension: $packageName")

        val classLoader = try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            // Use ClassLoader directly from context
            context.classLoader ?: return
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get class loader for $packageName", e)
            return
        }

        val factoryClass = try {
            Class.forName("$packageName.$FACTORY_CLASS_NAME", true, classLoader) as Class<out ScraperFactory>
        } catch (e: Exception) {
            Log.e(TAG, "Factory class not found: $packageName.$FACTORY_CLASS_NAME", e)
            return
        }

        val factory = try {
            factoryClass.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to instantiate factory for $packageName", e)
            return
        }

        val scraper = try {
            factory.create()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create scraper from $packageName", e)
            return
        }

        scrapers[scraper.id] = scraper
        extensionPackages[scraper.id] = packageName
        Log.d(TAG, "Successfully loaded scraper: ${scraper.id} (${scraper.name})")
    }

    /**
     * Reload a specific extension
     */
    suspend fun reloadExtension(scraperId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val packageName = extensionPackages[scraperId]
            if (packageName != null) {
                scrapers.remove(scraperId)
                loadExtension(packageName, context.packageManager)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Extension not found: $scraperId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Unload a specific extension
     */
    fun unloadExtension(scraperId: String) {
        scrapers.remove(scraperId)
        extensionPackages.remove(scraperId)
        Log.d(TAG, "Unloaded scraper: $scraperId")
    }

    /**
     * Get extension package name for a scraper ID
     */
    fun getExtensionPackage(scraperId: String): String? = extensionPackages[scraperId]

    /**
     * Check if extensions are loaded
     */
    fun isLoaded(): Boolean = scrapers.isNotEmpty()

    /**
     * Get count of loaded extensions
     */
    fun getExtensionCount(): Int = scrapers.size
}
