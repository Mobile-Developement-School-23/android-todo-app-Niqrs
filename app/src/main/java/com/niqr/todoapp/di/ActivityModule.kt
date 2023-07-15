package com.niqr.todoapp.di

import android.content.Context
import android.content.Intent
import com.niqr.core.di.AppScope
import com.niqr.other.alarm.di.MainActivityIntent
import com.niqr.todoapp.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {
    @Provides
    @AppScope
    @MainActivityIntent
    fun provideMainActivityIntent(
        context: Context
    ) = Intent(context, MainActivity::class.java)
}
