package com.niqr.todoapp.di

import com.niqr.auth.data.di.AuthDataModule
import com.niqr.auth.ui.di.AuthUiModule
import com.niqr.core.data.di.NetworkModule
import com.niqr.edit.ui.di.EditUiModule
import com.niqr.settings.data.di.SettingsDataModule
import com.niqr.tasks.data.di.TasksDataModule
import com.niqr.tasks.ui.di.TasksUiModule
import dagger.Module

/**
 * Contains all dagger modules
 */
@Module(includes = [
    NetworkModule::class,
    AuthDataModule::class, AuthUiModule::class,
    TasksDataModule::class, TasksUiModule::class,
    EditUiModule::class,
    SettingsDataModule::class
])
interface AppModule