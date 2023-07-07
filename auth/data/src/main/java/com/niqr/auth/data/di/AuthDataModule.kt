package com.niqr.auth.data.di

import com.niqr.auth.data.AuthRepositoryImpl
import com.niqr.auth.data.datastore.AuthInfoDataStoreManager
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides

@Module
object AuthDataModule {
    @Provides
    @AuthProviderScope
    fun provideAuthRepository(
        authProvider: AuthInfoMutableProvider
    ): AuthRepository {
        return AuthRepositoryImpl(authProvider)
    }

    @Provides
    @AuthProviderScope
    fun provideAuthInfoMutableProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoMutableProvider {
        return manager
    }

    @Provides
    @AuthProviderScope
    fun provideAuthInfoProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoProvider {
        return manager
    }
}