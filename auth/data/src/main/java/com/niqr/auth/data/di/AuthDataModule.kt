package com.niqr.auth.data.di

import com.niqr.auth.data.AuthRepositoryImpl
import com.niqr.auth.data.datastore.AuthInfoDataStoreManager
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthDataModule {
    @Binds
    @AuthProviderScope
    fun provideAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @AuthProviderScope
    fun provideAuthInfoMutableProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoMutableProvider

    @Binds
    @AuthProviderScope
    fun provideAuthInfoProvider(
        manager: AuthInfoDataStoreManager
    ): AuthInfoProvider
}