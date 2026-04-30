package com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore.UserPreferences
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = userPreferencesRepository.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences(isDarkMode = false, sortByTitle = true)
        )

    fun toggleDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    fun toggleSortByTitle(sortByTitle: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateSortByTitle(sortByTitle)
        }
    }
}
