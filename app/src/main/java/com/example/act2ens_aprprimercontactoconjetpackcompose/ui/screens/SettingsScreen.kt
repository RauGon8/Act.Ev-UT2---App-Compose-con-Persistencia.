package com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.act2ens_aprprimercontactoconjetpackcompose.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userPrefs by viewModel.userPreferences.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListItem(
                headlineContent = { Text("Modo Oscuro") },
                supportingContent = { Text("Cambia el tema de la aplicación") },
                trailingContent = {
                    Switch(
                        checked = userPrefs.isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode(it) }
                    )
                }
            )
            
            HorizontalDivider()
            
            ListItem(
                headlineContent = { Text("Ordenar por Título") },
                supportingContent = { Text("Si está desactivado, se ordena por fecha de edición") },
                trailingContent = {
                    Switch(
                        checked = userPrefs.sortByTitle,
                        onCheckedChange = { viewModel.toggleSortByTitle(it) }
                    )
                }
            )
        }
    }
}
