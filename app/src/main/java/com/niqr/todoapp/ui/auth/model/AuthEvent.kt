package com.niqr.todoapp.ui.auth.model

sealed class AuthEvent {
    object LaunchAuth: AuthEvent()
    object AuthSuccess: AuthEvent()
}