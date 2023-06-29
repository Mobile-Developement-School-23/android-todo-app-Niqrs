package com.niqr.todoapp.ui.auth

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val AuthScreenRoutePattern = "auth"

internal fun NavController.navigateToAuth() {
    this.navigate((AuthScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

internal fun NavGraphBuilder.authScreen(
    onSuccessAuth: () -> Unit
) {
    composable(AuthScreenRoutePattern) {
        val viewModel: AuthViewModel = hiltViewModel()
        AuthScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onSuccess = onSuccessAuth
        )
    }
}