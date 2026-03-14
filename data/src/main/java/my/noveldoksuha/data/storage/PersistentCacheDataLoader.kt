package my.noveldoksuha.data.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import my.noveldokusha.core.Response
import my.noveldokusha.core.asNotNull
import my.noveldokusha.core.flatMapError
import my.noveldokusha.core.tryAsResponse
import timber.log.Timber
import java.io.File

class PersistentCacheDataLoader<T>(
    private val cacheFile: File,
    private val serializer: kotlinx.serialization.KSerializer<T>
) {
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

    private suspend fun hasFile(): Boolean = withContext(Dispatchers.IO) { cacheFile.exists() }

    private suspend fun getFileContent(): Response<T> = tryAsResponse {
        withContext(Dispatchers.IO) {
            val jsonText = cacheFile.readText()
            json.decodeFromString(serializer, jsonText)
        }
    }.asNotNull()

    private suspend fun set(value: T) = tryAsResponse {
        withContext(Dispatchers.IO) {
            cacheFile.writeText(json.encodeToString(serializer, value))
        }
    }

    private suspend fun cache(fn: suspend () -> Response<T>): Response<T> {
        return when {
            hasFile() -> getFileContent()
                .onError { Timber.e(it.exception, it.message) }
                .flatMapError { fn() }
            else -> fn()
        }.onSuccess { set(it) }
    }

    suspend fun fetch(
        tryCache: Boolean = true,
        getRemote: suspend PersistentCacheDataLoader<T>.() -> Response<T>
    ): Response<T> = if (tryCache) cache { getRemote() } else getRemote()
}
