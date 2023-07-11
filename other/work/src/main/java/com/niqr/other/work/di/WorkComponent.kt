package com.niqr.other.work.di

import com.niqr.other.work.services.SynchronizationService
import dagger.Subcomponent

/**
 * Dagger component for work layer
 */
@Subcomponent
interface WorkComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WorkComponent
    }

    fun inject(service: SynchronizationService)
}

interface WorkComponentProvider {
    fun provideWorkComponent(): WorkComponent
}
