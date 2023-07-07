package com.niqr.auth.ui.di

import com.niqr.auth.domain.AuthRepository
import com.niqr.auth.ui.AuthViewModel
import com.niqr.core.di.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class AuthViewModelModule {

    @Provides
    @FeatureScope
    fun provideViewModel(
        repo: AuthRepository
    ): AuthViewModel = AuthViewModel(repo)
}