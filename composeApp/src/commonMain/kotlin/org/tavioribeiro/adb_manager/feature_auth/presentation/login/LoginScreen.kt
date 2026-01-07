package org.tavioribeiro.adb_manager.feature_auth.presentation.login

import adbmanager.composeapp.generated.resources.Res
import adbmanager.composeapp.generated.resources.logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.adb_manager.core_ui.components.buttons.IconTextButton
import org.tavioribeiro.adb_manager.core_ui.components.inputs.FullInput
import org.tavioribeiro.adb_manager.core_ui.components.toast.CustomToastView
import org.tavioribeiro.adb_manager.core_ui.theme.AppTheme
import org.tavioribeiro.adb_manager.feature_parking.presentation.dashboard.DashboardScreen

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = getScreenModel<LoginScreenModel>()
        val state by screenModel.uiState.collectAsState()

        // Estados locais para os inputs
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }


        LaunchedEffect(state.isLoggedIn) {
            if (state.isLoggedIn) {
                navigator.replaceAll(DashboardScreen())
            }
        }

        Scaffold(
            containerColor = AppTheme.colors.color1
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 450.dp)
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo
                    /*Icon(
                        imageVector = Icons.Default.LocalParking,
                        contentDescription = "Logo",
                        tint = AppTheme.colors.color7, // Verde Vibrante
                        modifier = Modifier.size(80.dp)
                    )*/
                    Image(
                        painter = painterResource(Res.drawable.logo),
                        contentDescription = "Logo ADB Manager",
                        modifier = Modifier
                            .width(250.dp)
                            .heightIn(max = 100.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(32.dp))


                    FullInput(
                        title = "E-mail",
                        placeholder = "Digite seu e-mail",
                        initialValue = email,
                        onValueChange = { email = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isBackgroudColorDark = true
                    )

                    FullInput(
                        modifier = Modifier.padding(top = 16.dp),
                        title = "Senha",
                        placeholder = "Digite sua senha",
                        initialValue = password,
                        onValueChange = { password = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isBackgroudColorDark = true
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        IconTextButton(
                            modifier = Modifier
                                .width(160.dp)
                                .height(50.dp),
                            text = "ENTRAR",
                            onClick = {
                                screenModel.login(email, password)
                            },
                            icon = rememberVectorPainter(Icons.Default.ArrowForward),
                            isLoading = state.isLoading,
                            enabled = !state.isLoading
                        )
                    }
                }

                if (state.toast != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 24.dp)
                    ) {
                        CustomToastView(state.toast!!)
                        LaunchedEffect(state.toast) {
                            delay(3000)
                            screenModel.clearToast()
                        }
                    }
                }
            }
        }
    }
}