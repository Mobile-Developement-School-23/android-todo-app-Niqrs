package com.niqr.edit.ui.di

import com.niqr.core.di.FeatureScope
import com.niqr.edit.ui.TaskEditViewModel
import dagger.Subcomponent

/**
 * Dagger component for edit ui layer
 */
@Subcomponent
@FeatureScope
interface EditUiComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): EditUiComponent
    }

    fun getViewModel(): TaskEditViewModel
}

interface EditUiComponentProvider {
    fun provideEditUiComponent(): EditUiComponent
}
