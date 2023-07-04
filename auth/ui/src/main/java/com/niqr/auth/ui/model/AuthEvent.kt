package com.niqr.auth.ui.model

sealed class AuthEvent {
    object LaunchAuth: AuthEvent()
    object AuthSuccess: AuthEvent()
}