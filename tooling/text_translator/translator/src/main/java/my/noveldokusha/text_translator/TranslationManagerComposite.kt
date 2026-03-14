package my.noveldokusha.text_translator

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import my.noveldokusha.core.AppCoroutineScope
import my.noveldokusha.core.appPreferences.AppPreferences
import my.noveldokusha.text_translator.domain.TranslationManager
import my.noveldokusha.text_translator.domain.TranslationModelState
import my.noveldokusha.text_translator.domain.TranslatorState

/**
 * Translation manager using only ML Kit offline translation.
 * No online API dependencies.
 */
class TranslationManagerComposite(
    private val coroutineScope: AppCoroutineScope,
    private val mlkitManager: TranslationManagerMLKit,
    private val appPreferences: AppPreferences
) : TranslationManager {

    companion object {
        private const val TAG = "TranslationComposite"
    }

    override val available: Boolean
        get() = mlkitManager.available

    override val isUsingOnlineTranslation: Boolean
        get() = false

    override val models = mutableStateListOf<TranslationModelState>()

    init {
        // Use MLKit models directly
        models.addAll(mlkitManager.models.map { model ->
            TranslationModelState(
                language = model.language,
                available = model.available,
                downloading = model.downloading,
                downloadingFailed = model.downloadingFailed,
                displayName = model.displayName
            )
        })
    }

    override suspend fun hasModelDownloaded(language: String): TranslationModelState? {
        return mlkitManager.hasModelDownloaded(language)
    }

    override fun getTranslator(source: String, target: String): TranslatorState {
        Log.d(TAG, "getTranslator: source=$source, target=$target")
        
        return try {
            Log.d(TAG, "Creating MLKit translator")
            mlkitManager.getTranslator(source, target)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create MLKit translator", e)
            throw e
        }
    }

    override fun downloadModel(language: String) {
        mlkitManager.downloadModel(language)
    }

    override fun removeModel(language: String) {
        mlkitManager.removeModel(language)
    }

    override fun close() {
        mlkitManager.close()
    }
}
