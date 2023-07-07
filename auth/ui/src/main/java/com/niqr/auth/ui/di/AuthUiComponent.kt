package com.niqr.auth.ui.di

import com.niqr.auth.ui.AuthViewModel
import com.niqr.core.di.FeatureScope
import dagger.Subcomponent

@Subcomponent
@FeatureScope
interface AuthUiComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthUiComponent
    }

    fun getViewModel(): AuthViewModel
}

interface AuthUiComponentProvider {
    fun provideAuthUiComponent(): AuthUiComponent
}
