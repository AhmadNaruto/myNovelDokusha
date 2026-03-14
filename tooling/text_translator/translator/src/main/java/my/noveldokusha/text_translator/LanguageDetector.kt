package my.noveldokusha.text_translator

import android.util.Log
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

/**
 * Utility class for detecting language of text using ML Kit Language Identification.
 * Uses Kotlin coroutines await() extension from kotlinx-coroutines-play-services
 * to convert Task-based ML Kit APIs into suspend functions.
 * 
 * Supports auto-detection of source language for translation.
 * ML Kit recognizes 100+ languages and returns BCP-47 language codes.
 * 
 * Usage:
 * ```
 * val detector = LanguageDetector()
 * val language = detector.detectLanguage("Hello world") // returns "en"
 * ```
 */
class LanguageDetector {

    companion object {
        private const val TAG = "LanguageDetector"
        
        /**
         * Special language code for auto-detect.
         * When used as source language, the translator will auto-detect the source.
         */
        const val AUTO_DETECT_LANGUAGE = "auto"
        
        /**
         * Minimum text length for reliable language detection.
         * Text shorter than this may give inaccurate results.
         */
        private const val MIN_TEXT_LENGTH = 3
        
        /**
         * Default confidence threshold for language detection.
         * Languages with confidence below this are considered unreliable.
         */
        private const val DEFAULT_CONFIDENCE_THRESHOLD = 0.5f
    }

    private val languageIdentifier by lazy {
        LanguageIdentification.getClient(
            LanguageIdentificationOptions.Builder()
                .setConfidenceThreshold(DEFAULT_CONFIDENCE_THRESHOLD)
                .build()
        )
    }

    /**
     * Detect the language of the given text.
     * 
     * @param text The text to analyze
     * @return BCP-47 language code (e.g., "en", "zh", "ja") or null if unable to detect
     */
    suspend fun detectLanguage(text: String): String? = withContext(Dispatchers.IO) {
        try {
            if (text.length < MIN_TEXT_LENGTH) {
                Log.w(TAG, "Text too short for language detection: ${text.length} chars")
                return@withContext null
            }

            val languageCode = languageIdentifier.identifyLanguage(text).await()
            
            if (languageCode == "und") {
                Log.w(TAG, "Unable to determine language for text: ${text.take(50)}...")
                null
            } else {
                Log.d(TAG, "Detected language: $languageCode (confidence >= $DEFAULT_CONFIDENCE_THRESHOLD)")
                languageCode
            }
        } catch (e: Exception) {
            Log.e(TAG, "Language detection failed", e)
            null
        }
    }

    /**
     * Detect multiple possible languages for the given text with confidence scores.
     *
     * @param text The text to analyze
     * @return List of identified languages with language codes and confidence scores
     */
    suspend fun detectPossibleLanguages(text: String): List<com.google.mlkit.nl.languageid.IdentifiedLanguage> = withContext(Dispatchers.IO) {
        try {
            if (text.length < MIN_TEXT_LENGTH) {
                Log.w(TAG, "Text too short for language detection: ${text.length} chars")
                return@withContext emptyList()
            }

            val languages = languageIdentifier.identifyPossibleLanguages(text).await()

            Log.d(TAG, "Detected ${languages.size} possible languages:")
            languages.forEach { lang ->
                Log.d(TAG, "  - ${lang.languageTag}: ${lang.confidence}")
            }

            languages
        } catch (e: Exception) {
            Log.e(TAG, "Language detection failed", e)
            emptyList()
        }
    }

    /**
     * Detect language and return the primary (most confident) language.
     * 
     * @param text The text to analyze
     * @return Primary language code or null if unable to detect
     */
    suspend fun detectPrimaryLanguage(text: String): String? = withContext(Dispatchers.IO) {
        try {
            val languages = detectPossibleLanguages(text)
            languages.maxByOrNull { it.confidence }?.languageTag
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get primary language", e)
            null
        }
    }

    /**
     * Check if detected language matches the expected language.
     * Useful for validating user-selected source language.
     * 
     * @param text The text to analyze
     * @param expectedLanguage Expected BCP-47 language code
     * @return true if detected language matches expected language with good confidence
     */
    suspend fun isLanguage(text: String, expectedLanguage: String): Boolean {
        val detectedLanguage = detectLanguage(text)
        val matches = detectedLanguage?.startsWith(expectedLanguage.take(2)) == true
        Log.d(TAG, "Language check: expected=$expectedLanguage, detected=$detectedLanguage, matches=$matches")
        return matches
    }

    /**
     * Close the language identifier and release resources.
     * Call this when the detector is no longer needed.
     */
    fun close() {
        try {
            languageIdentifier.close()
            Log.d(TAG, "LanguageDetector closed")
        } catch (e: Exception) {
            Log.e(TAG, "Error closing LanguageDetector", e)
        }
    }
}
