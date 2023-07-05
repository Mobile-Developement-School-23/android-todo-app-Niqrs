package com.niqr.auth.ui.model

sealed class AuthAction {
    object AuthClick: AuthAction()
    data class AuthResult(val token: String?): AuthAction()
}
