package my.noveldokusha.scraper.api.utils

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.security.KeyStore
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * Create an OkHttpClient suitable for web scraping
 * Includes proper timeouts, user agent, and TLS configuration
 */
fun createScraperHttpClient(
    connectTimeout: Long = 30,
    readTimeout: Long = 30,
    writeTimeout: Long = 30,
    userAgent: String = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36",
    followRedirects: Boolean = true,
    enableTls12: Boolean = true
): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .writeTimeout(writeTimeout, TimeUnit.SECONDS)
        .followRedirects(followRedirects)
        .followSslRedirects(followRedirects)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", userAgent)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Connection", "keep-alive")
                .build()
            chain.proceed(request)
        }

    if (enableTls12) {
        builder.enableTls12()
    }

    return builder.build()
}

/**
 * Enable TLS 1.2 for older servers
 */
private fun OkHttpClient.Builder.enableTls12(): OkHttpClient.Builder {
    try {
        val sc = SSLContext.getInstance("TLSv1.2")
        sc.init(null, null, null)
        sslSocketFactory(sc.socketFactory, createTrustManager())

        val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .build()

        connectionSpecs(listOf(cs, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
    } catch (e: Exception) {
        // Ignore and use default SSL
    }

    return this
}

private fun createTrustManager(): X509TrustManager {
    return try {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(null as KeyStore?)
        val trustManagers = tmf.trustManagers
        trustManagers[0] as X509TrustManager
    } catch (e: Exception) {
        // Fallback to unsafe trust manager
        UnsafeTrustManager()
    }
}

@Suppress("CustomX509TrustManager")
private class UnsafeTrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
        // Trust all
    }

    override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
        // Trust all
    }

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
        return emptyArray()
    }
}
