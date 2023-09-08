package com.niqr.other.alarm.di

import com.niqr.other.alarm.AlarmReceiver
import dagger.Subcomponent

/**
 * Dagger component for alarm layer
 */
@Subcomponent
interface AlarmComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AlarmComponent
    }

    fun inject(service: AlarmReceiver)
}

interface AlarmComponentProvider {
    fun provideAlarmComponent(): AlarmComponent
}
