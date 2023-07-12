package com.niqr.settings.data.di

import com.niqr.core.di.AppScope
import com.niqr.settings.data.AppSettingsDataStoreManager
import com.niqr.settings.domain.settings.AppSettingsMutableProvider
import com.niqr.settings.domain.settings.AppSettingsProvider
import dagger.Binds
import dagger.Module

@Module
interface SettingsDataModule {

    @Binds
    @AppScope
    fun provideSettingsProvider(
        settingsManager: AppSettingsDataStoreManager
    ): AppSettingsProvider

    @Binds
    @AppScope
    fun provideMutableSettingsProvider(
        settingsManager: AppSettingsDataStoreManager
    ): AppSettingsMutableProvider
}