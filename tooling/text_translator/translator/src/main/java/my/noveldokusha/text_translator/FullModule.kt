package my.noveldokusha.text_translator

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.noveldokusha.core.AppCoroutineScope
import my.noveldokusha.core.appPreferences.AppPreferences
import my.noveldokusha.text_translator.domain.TranslationManager
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FullModule {

    @Provides
    @Singleton
    fun provideTranslationManager(
        coroutineScope: AppCoroutineScope,
        appPreferences: AppPreferences
    ): TranslationManager {
        val mlkitManager = TranslationManagerMLKit(coroutineScope)

        // Use MLKit only (offline translation)
        return TranslationManagerComposite(
            coroutineScope,
            mlkitManager,
            appPreferences
        )
    }
}