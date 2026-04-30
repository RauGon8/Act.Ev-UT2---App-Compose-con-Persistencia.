package com.example.act2ens_aprprimercontactoconjetpackcompose

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.PeliculasViewModel
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens.SettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer<PeliculasViewModel> {
            PeliculasViewModel(
                peliculaApplication().container.peliculaRepository,
                peliculaApplication().container.userPreferencesRepository
            )
        }
        initializer<SettingsViewModel> {
            SettingsViewModel(
                peliculaApplication().container.userPreferencesRepository
            )
        }
    }
}

fun CreationExtras.peliculaApplication(): PeliculaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PeliculaApplication)
