package com.niqr.other.work.di

import javax.inject.Scope

/**
 * Dagger scope for providing synchronization workers
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SynchronizationWorkScope