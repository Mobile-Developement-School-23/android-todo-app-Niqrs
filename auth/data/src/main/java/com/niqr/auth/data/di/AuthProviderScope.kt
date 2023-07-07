package com.niqr.auth.data.di

import javax.inject.Scope

/**
 * Dagger scope for providing authentication info
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthProviderScope