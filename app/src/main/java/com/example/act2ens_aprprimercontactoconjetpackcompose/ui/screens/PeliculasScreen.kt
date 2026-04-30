package com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.act2ens_aprprimercontactoconjetpackcompose.AppViewModelProvider
import com.example.act2ens_aprprimercontactoconjetpackcompose.domain.model.Pelicula

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeliculasScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToForm: (Int?) -> Unit,
    viewModel: PeliculasViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    // Estado para el diálogo de confirmación de borrado
    var peliculaABorrar by remember { mutableStateOf<Pelicula?>(null) }

    // Diálogo de confirmación al borrar
    if (peliculaABorrar != null) {
        AlertDialog(
            onDismissRequest = { peliculaABorrar = null },
            title = { Text("Confirmar borrado") },
            text = { Text("¿Seguro que quieres borrar \"${peliculaABorrar!!.titulo}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePelicula(peliculaABorrar!!)
                    peliculaABorrar = null
                }) {
                    Text("Borrar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { peliculaABorrar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Películas") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToForm(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Película")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // --- Barra de búsqueda ---
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar película...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true
            )

            // --- Chip de filtro de favoritos ---
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.showFavoritesOnly,
                    onClick = { viewModel.toggleShowFavoritesOnly() },
                    label = { Text("Solo Favoritos") },
                    leadingIcon = {
                        Icon(
                            imageVector = if (uiState.showFavoritesOnly) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }

            // --- Contenido principal ---
            if (uiState.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.peliculas.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        if (uiState.showFavoritesOnly) "No tienes películas favoritas"
                        else if (uiState.searchQuery.isNotEmpty()) "No se encontraron resultados"
                        else "No hay películas. ¡Añade una!"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.peliculas, key = { it.id }) { pelicula ->
                        PeliculaItem(
                            pelicula = pelicula,
                            onDelete = { peliculaABorrar = pelicula },
                            onToggleFavorite = { viewModel.toggleFavorite(pelicula) },
                            onClick = { onNavigateToForm(pelicula.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PeliculaItem(
    pelicula: Pelicula,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pelicula.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = pelicula.genero,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (pelicula.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (pelicula.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
            
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

