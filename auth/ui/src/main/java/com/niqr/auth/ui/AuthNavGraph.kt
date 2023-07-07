package com.niqr.auth.ui

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.niqr.auth.ui.di.AuthUiComponentProvider
import com.niqr.core.ui.utils.daggerViewModel

const val AuthScreenRoutePattern = "auth"

fun NavController.navigateToAuth() {
    this.navigate((AuthScreenRoutePattern)) {
        popUpTo(0)
        launchSingleTop = true
    }
}

/**
 * Authentication navigation graph
 */
fun NavGraphBuilder.authScreen(
    onSuccessAuth: () -> Unit
) {
    composable(AuthScreenRoutePattern) {
        val context = LocalContext.current
        val viewModel: AuthViewModel = daggerViewModel {
            (context.applicationContext as AuthUiComponentProvider)
                .provideAuthUiComponent().getViewModel()
        }

        AuthScreen(
            uiState = viewModel.uiState,
            uiEvent = viewModel.uiEvent,
            onAction = viewModel::onAction,
            onSuccess = onSuccessAuth
        )
    }
}
