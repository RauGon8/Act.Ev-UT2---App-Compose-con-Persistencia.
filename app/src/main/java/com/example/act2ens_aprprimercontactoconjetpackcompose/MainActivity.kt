package com.example.act2ens_aprprimercontactoconjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.navigation.FormularioPelicula
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.navigation.ListaPeliculas
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.navigation.Settings
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.FormularioPeliculaScreen
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.PeliculasScreen
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.SettingsScreen
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.SettingsViewModel
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val userPrefs by settingsViewModel.userPreferences.collectAsState()
            
            val useDarkTheme = userPrefs.isDarkMode

            AppTheme(darkTheme = useDarkTheme) {
                val navController = rememberNavController()
                
                NavHost(
                    navController = navController,
                    startDestination = ListaPeliculas
                ) {
                    composable<ListaPeliculas> {
                        PeliculasScreen(
                            onNavigateToSettings = { navController.navigate(Settings) },
                            onNavigateToForm = { id -> navController.navigate(FormularioPelicula(id)) }
                        )
                    }
                    
                    composable<FormularioPelicula> { backStackEntry ->
                        val route: FormularioPelicula = backStackEntry.toRoute()
                        FormularioPeliculaScreen(
                            peliculaId = route.peliculaId,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    
                    composable<Settings> {
                        SettingsScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
