package com.niqr.auth.ui.model

/**
 * Provides info for auth ui events
 */
sealed class AuthEvent {
    object LaunchAuth: AuthEvent()
    object AuthSuccess: AuthEvent()
}
