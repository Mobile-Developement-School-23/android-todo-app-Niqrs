package com.niqr.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.niqr.auth.ui.model.AuthAction
import com.niqr.auth.ui.model.AuthEvent
import com.niqr.auth.ui.model.AuthUiState
import com.niqr.core.ui.R.string.app_name
import com.niqr.core.ui.theme.Blue
import com.niqr.core.ui.theme.ExtendedTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    uiState: AuthUiState,
    uiEvent: Flow<AuthEvent>,
    onAction: (AuthAction) -> Unit,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current

    val authSdk = remember(Unit) {
        YandexAuthSdk(context, YandexAuthOptions(context, true))
    }
    val authIntent = remember(Unit) {
        val loginOptionsBuilder = YandexAuthLoginOptions.Builder()
        authSdk.createLoginIntent(loginOptionsBuilder.build())
    }

    val authLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        val yandexAuthToken = authSdk.extractToken(it.resultCode, it.data)
        onAction(AuthAction.AuthResult(yandexAuthToken?.value))
    }

    LaunchedEffect(Unit) {
        uiEvent.collect {
            when(it) {
                AuthEvent.LaunchAuth -> authLauncher.launch(authIntent)
                AuthEvent.AuthSuccess -> onSuccess()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(app_name))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = ExtendedTheme.colors.backPrimary,
                    titleContentColor = ExtendedTheme.colors.labelPrimary,
                    actionIconContentColor = Blue
                )
            )
        },
        containerColor = ExtendedTheme.colors.backPrimary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onAction(AuthAction.AuthClick) },
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(28),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ExtendedTheme.colors.backSecondary
                ),
                border = BorderStroke(1.5.dp, ExtendedTheme.colors.labelPrimary),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(29.dp),
                        painter = painterResource(R.drawable.ic_yandex_logo),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.login_with_yandex_id),
                        style = MaterialTheme.typography.headlineMedium,
                        color = ExtendedTheme.colors.labelPrimary
                    )
                }
            }
        }
    }
}
