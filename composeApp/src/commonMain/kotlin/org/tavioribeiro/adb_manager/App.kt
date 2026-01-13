package org.tavioribeiro.adb_manager

import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.tavioribeiro.adb_manager.core_ui.theme.MyAppTheme
import org.tavioribeiro.adb_manager.feature_main.presentation.dashboard.DashboardScreen


@Composable
fun App() {
    MyAppTheme {
        /*val authRepository = koinInject<AuthRepository>()

        val initialScreen = if (authRepository.getCurrentSession() != null) {
            DashboardScreen()
        } else {
            MainScreen()
        }*/

        val initialScreen = DashboardScreen()

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
