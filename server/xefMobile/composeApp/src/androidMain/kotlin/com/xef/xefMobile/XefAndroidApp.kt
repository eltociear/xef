package com.xef.xefMobile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.server.movile.xef.android.ui.screens.*
import com.server.movile.xef.android.ui.screens.menu.AssistantScreen
import com.server.movile.xef.android.ui.screens.menu.CreateAssistantScreen
import com.server.movile.xef.android.ui.screens.navigationdrawercompose.HomeScreen
import com.server.movile.xef.android.ui.viewmodels.IAuthViewModel
import com.xef.xefMobile.ui.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun XefAndroidApp(authViewModel: IAuthViewModel) {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = Screens.Login.screen,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        composable(Screens.Start.screen) { StartScreen() }
        composable(Screens.Login.screen) { LoginScreen(authViewModel, navigationController) }
        composable(Screens.Register.screen) { RegisterScreen(authViewModel, navigationController) }
        composable(Screens.Home.screen) { HomeScreen(authViewModel, navigationController) }
        composable(Screens.Assistants.screen) {
            MainLayout(navController = navigationController, authViewModel = authViewModel) {
                AssistantScreen(navigationController)
            }
        }
        composable(Screens.CreateAssistant.screen) {
            MainLayout(navController = navigationController, authViewModel = authViewModel) {
                CreateAssistantScreen(navigationController)
            }
        }
        // ... other composable screens ...
    }
}