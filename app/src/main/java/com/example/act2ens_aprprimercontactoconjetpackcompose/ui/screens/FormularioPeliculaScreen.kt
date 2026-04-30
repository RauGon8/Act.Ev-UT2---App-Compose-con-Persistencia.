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
import com.example.act2ens_aprprimercontactoconjetpackcompose.domain.model.Pelicula

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPeliculaScreen(
    peliculaId: Int?,
    onNavigateBack: () -> Unit,
    viewModel: PeliculasViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(peliculaId != null) }

    LaunchedEffect(peliculaId) {
        if (peliculaId != null) {
            val pelicula = viewModel.getPeliculaById(peliculaId)
            if (pelicula != null) {
                titulo = pelicula.titulo
                genero = pelicula.genero
                isFavorite = pelicula.isFavorite
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (peliculaId == null) "Nueva Película" else "Editar Película") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(innerPadding)) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isFavorite, onCheckedChange = { isFavorite = it })
                    Text("Marcar como favorita")
                }
                
                Button(
                    onClick = {
                        if (titulo.isNotBlank() && genero.isNotBlank()) {
                            viewModel.upsertPelicula(
                                Pelicula(
                                    id = peliculaId ?: 0,
                                    titulo = titulo,
                                    genero = genero,
                                    isFavorite = isFavorite,
                                    updatedAt = System.currentTimeMillis()
                                )
                            )
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
