package com.niqr.auth.data.di

import com.niqr.auth.data.AuthRepositoryImpl
import com.niqr.auth.data.datastore.AuthInfoDataStoreManager
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.auth.domain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        authProvider: AuthInfoMutableProvider
    ): AuthRepository {
        return AuthRepositoryImpl(authProvider)
    }

    @Provides
    @Singleton
    fun provideAuthInfoMutableProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoMutableProvider {
        return manager
    }

    @Provides
    @Singleton
    fun provideAuthInfoProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoProvider {
        return manager
    }
}