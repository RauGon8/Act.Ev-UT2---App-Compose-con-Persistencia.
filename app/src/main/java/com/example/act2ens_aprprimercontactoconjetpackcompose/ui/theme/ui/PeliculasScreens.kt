package com.example.act2ens_aprprimercontactoconjetpackcompose.ui.peliculas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database.PeliculaEntity
import com.example.act2ens_aprprimercontactoconjetpackcompose.ui.PeliculaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPeliculasScreen(
    viewModel: PeliculaViewModel,
    onNavegarAlFormulario: () -> Unit
) {
    val peliculas by viewModel.misPeliculas.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Mis Películas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.limpiarFormulario()
                onNavegarAlFormulario()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(peliculas) { pelicula ->
                ItemPelicula(
                    pelicula = pelicula,
                    onDelete = { viewModel.eliminarPelicula(pelicula) },
                    onEdit = {
                        viewModel.prepararEdicion(pelicula)
                        onNavegarAlFormulario()
                    },
                    onFavorite = { viewModel.cambiarFavorito(pelicula) }
                )
            }
        }
    }
}


@Composable
fun ItemPelicula(
    pelicula: PeliculaEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = pelicula.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = pelicula.genero, style = MaterialTheme.typography.bodyMedium)
            }


            Row {
                IconButton(onClick = onFavorite) {
                    Icon(
                        imageVector = if (pelicula.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (pelicula.isFavorite) Color.Red else Color.Gray
                    )
                }
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPeliculaScreen(
    viewModel: PeliculaViewModel,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(if (viewModel.esModoEdicion()) "Editar Película" else "Nueva Película")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = viewModel.tituloActual,
                onValueChange = { viewModel.tituloActual = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = viewModel.generoActual,
                onValueChange = { viewModel.generoActual = it },
                label = { Text("Género") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.guardarPelicula()
                    onVolver()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (viewModel.esModoEdicion()) "Guardar Cambios" else "Crear Película")
            }

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}