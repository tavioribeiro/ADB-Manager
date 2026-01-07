package org.tavioribeiro.adb_manager

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.tavioribeiro.adb_manager.core_ui.theme.MyAppTheme
import org.tavioribeiro.adb_manager.feature_auth.domain.repository.AuthRepository
import org.tavioribeiro.adb_manager.feature_auth.presentation.login.LoginScreen
import org.tavioribeiro.adb_manager.feature_parking.presentation.dashboard.DashboardScreen
import org.koin.compose.koinInject


@Composable
fun App() {
    MyAppTheme {
        val authRepository = koinInject<AuthRepository>()

        val initialScreen = if (authRepository.getCurrentSession() != null) {
            DashboardScreen()
        } else {
            LoginScreen()
        }

        Navigator(initialScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}
/*
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}*/
