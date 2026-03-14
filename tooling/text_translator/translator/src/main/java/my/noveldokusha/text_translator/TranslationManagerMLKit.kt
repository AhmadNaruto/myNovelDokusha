package my.noveldokusha.text_translator

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import my.noveldokusha.core.AppCoroutineScope
import my.noveldokusha.text_translator.domain.TranslationManager
import my.noveldokusha.text_translator.domain.TranslationModelState
import my.noveldokusha.text_translator.domain.TranslatorState

class TranslationManagerMLKit(
    private val coroutineScope: AppCoroutineScope
) : TranslationManager {

    companion object {
        private const val TAG = "TranslationManagerMLKit"
        
        /**
         * Special language code for auto-detect source language.
         * ML Kit Translation supports auto-detection when source language is set to "und" (undetermined).
         */
        const val SOURCE_LANGUAGE_AUTO = "und"
    }

    private val languageDetector = LanguageDetector()

    override val available = true

    override val models =
        mutableStateListOf<TranslationModelState>().apply {
            val list = try {
                TranslateLanguage.getAllLanguages().map {
                    TranslationModelState(
                        language = it,
                        available = false,
                        downloading = false,
                        downloadingFailed = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to get available languages from ML Kit", e)
                emptyList()
            }
            addAll(list)
            
            // Add auto-detect option as the first item
            add(
                0,
                TranslationModelState(
                    language = SOURCE_LANGUAGE_AUTO,
                    available = true,
                    downloading = false,
                    downloadingFailed = false,
                    displayName = "Auto-detect"
                )
            )
        }

    init {
        coroutineScope.launch {
            try {
                val downloaded = loadDownloadedModelsList().associateBy { it.language }
                models.replaceAll {
                    it.copy(available = downloaded.containsKey(it.language))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load downloaded models", e)
            }
        }
    }

    private suspend fun loadDownloadedModelsList() = withContext(Dispatchers.IO) {
        try {
            RemoteModelManager
                .getInstance()
                .getDownloadedModels(TranslateRemoteModel::class.java)
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading downloaded models", e)
            emptyList()
        }
    }


    override suspend fun hasModelDownloaded(language: String): TranslationModelState? = withContext(Dispatchers.IO) {
        try {
            val model = TranslateRemoteModel.Builder(language).build()
            val isDownloaded = RemoteModelManager
                .getInstance()
                .isModelDownloaded(model)
                .await()
            if (isDownloaded) {
                TranslationModelState(
                    available = isDownloaded,
                    downloading = false,
                    language = language,
                    downloadingFailed = false
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if model is downloaded for $language", e)
            null
        }
    }

    override fun getTranslator(
        source: String,
        target: String
    ): TranslatorState {
        try {
            // ML Kit supports auto-detect when source language is "und" (undetermined)
            val sourceLanguage = if (source == LanguageDetector.AUTO_DETECT_LANGUAGE) {
                SOURCE_LANGUAGE_AUTO
            } else {
                source
            }
            
            val option = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(target)
                .build()

            val translator = Translation.getClient(option)

            return TranslatorState(
                source = source,
                target = target,
                translate = { input ->
                    try {
                        // If using auto-detect, ML Kit will automatically detect the source language
                        translator.translate(input).await()
                    } catch (e: Exception) {
                        Log.e(TAG, "Translation failed for source=$sourceLanguage, target=$target", e)
                        throw e
                    }
                },
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create translator for source=$source, target=$target", e)
            throw e
        }
    }

    /**
     * Detect the language of the given text.
     * Useful for pre-checking source language before translation.
     */
    suspend fun detectLanguage(text: String): String? {
        return languageDetector.detectLanguage(text)
    }

    /**
     * Detect multiple possible languages with confidence scores.
     */
    suspend fun detectPossibleLanguages(text: String): List<com.google.mlkit.nl.languageid.IdentifiedLanguage> {
        return languageDetector.detectPossibleLanguages(text)
    }

    override fun downloadModel(language: String) {
        try {
            coroutineScope.launch {
                val index = models.indexOfFirst { it.language == language }
                if (index == -1 || models[index].available) return@launch

                models[index] = models[index].copy(
                    downloadingFailed = false,
                    downloading = true,
                )

                RemoteModelManager
                    .getInstance()
                    .download(
                        TranslateRemoteModel.Builder(language).build(),
                        DownloadConditions.Builder().build()
                    )
                    .addOnSuccessListener {
                        models[index] = models[index].copy(
                            downloadingFailed = false,
                            downloading = false,
                            available = true
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to download model for $language", e)
                        models[index] = models[index].copy(
                            downloadingFailed = true,
                            downloading = false
                        )
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting download for $language", e)
            val index = models.indexOfFirst { it.language == language }
            if (index != -1) {
                models[index] = models[index].copy(
                    downloadingFailed = true,
                    downloading = false
                )
            }
        }
    }

    override fun removeModel(language: String) {
        try {
            coroutineScope.launch {

                // English can't be removed.
                if (language == "en") return@launch

                val index = models.indexOfFirst { it.language == language }
                if (index == -1 || !models[index].available)
                    return@launch

                RemoteModelManager
                    .getInstance()
                    .deleteDownloadedModel(TranslateRemoteModel.Builder(language).build())
                    .addOnSuccessListener {
                        models[index] = models[index].copy(
                            downloadingFailed = false,
                            downloading = false,
                            available = false
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to remove model for $language", e)
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error removing model for $language", e)
        }
    }

    /**
     * Close the language detector and release resources.
     * Call this when the manager is no longer needed.
     */
    override fun close() {
        languageDetector.close()
    }
}
