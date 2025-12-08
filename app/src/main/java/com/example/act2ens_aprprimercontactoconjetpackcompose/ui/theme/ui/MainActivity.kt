package com.example.act2ens_aprprimercontactoconjetpackcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.navigation.RutaFormularioPelicula
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.navigation.RutaListaPeliculas
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.peliculas.FormularioPeliculaScreen
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.peliculas.ListaPeliculasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewModel: PeliculaViewModel by viewModels { PeliculaViewModel.Factory }

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = RutaListaPeliculas
            ) {

                composable<RutaListaPeliculas> {
                    ListaPeliculasScreen(
                        viewModel = viewModel,
                        onNavegarAlFormulario = {
                            navController.navigate(RutaFormularioPelicula)
                        }
                    )
                }


                composable<RutaFormularioPelicula> {
                    FormularioPeliculaScreen(
                        viewModel = viewModel,
                        onVolver = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}