package com.example.act2ens_aprprimercontactoconjetpackcompose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.act2ens_aprprimercontactoconjetpackcompose.PeliculaApp
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database.PeliculaEntity
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.repositories.PeliculaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PeliculaViewModel(private val repository: PeliculaRepository) : ViewModel() {

    // --- ESTADO DE LA LISTA (Lectura) ---
    val misPeliculas: StateFlow<List<PeliculaEntity>> = repository.peliculas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- ESTADO DEL FORMULARIO (Escritura/Edición) ---
    // Estas variables están vinculadas a los TextField de la UI
    var tituloActual by mutableStateOf("")
    var generoActual by mutableStateOf("")

    // Si es null = Creando nueva. Si tiene ID = Editando existente.
    private var idPeliculaEditando: Int? = null
    private var isFavoriteActual: Boolean = false

    // --- LÓGICA PRINCIPAL: GUARDAR ---
    fun guardarPelicula() {
        if (tituloActual.isBlank() || generoActual.isBlank()) return

        viewModelScope.launch {
            if (idPeliculaEditando == null) {
                // MODO CREAR
                repository.agregarPelicula(tituloActual, generoActual)
            } else {
                // MODO ACTUALIZAR
                val peliculaEditada = PeliculaEntity(
                    id = idPeliculaEditando!!, // Importante: Mantener el mismo ID
                    titulo = tituloActual,
                    genero = generoActual,
                    isFavorite = isFavoriteActual
                )
                repository.actualizarPelicula(peliculaEditada)
            }
            limpiarFormulario()
        }
    }

    // --- PREPARAR PARA EDITAR ---
    // Se llama cuando pulsas el lápiz en la lista
    fun prepararEdicion(pelicula: PeliculaEntity) {
        tituloActual = pelicula.titulo
        generoActual = pelicula.genero
        idPeliculaEditando = pelicula.id
        isFavoriteActual = pelicula.isFavorite
    }

    // --- LIMPIAR / CANCELAR ---
    fun limpiarFormulario() {
        tituloActual = ""
        generoActual = ""
        idPeliculaEditando = null
        isFavoriteActual = false
    }

    // Saber si estamos en modo edición para cambiar el texto del botón (UI helper)
    fun esModoEdicion(): Boolean {
        return idPeliculaEditando != null
    }

    // --- OTRAS ACCIONES ---
    fun eliminarPelicula(pelicula: PeliculaEntity) {
        viewModelScope.launch { repository.borrarPelicula(pelicula) }
    }

    fun cambiarFavorito(pelicula: PeliculaEntity) {
        viewModelScope.launch { repository.toggleFavorito(pelicula) }
    }

    // --- FACTORY ---
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PeliculaApp)
                PeliculaViewModel(app.repository)
            }
        }
    }
}