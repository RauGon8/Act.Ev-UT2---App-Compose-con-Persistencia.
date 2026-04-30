package com.example.act2ens_aprprimercontactoconjetpackcompose.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore.UserPreferencesRepository
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.repository.PeliculaRepository
import com.example.act2ens_aprprimercontactoconjetpackcompose.domain.model.Pelicula
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PeliculasUiState(
    val peliculas: List<Pelicula> = emptyList(),
    val isLoading: Boolean = false
)

class PeliculasViewModel(
    private val peliculaRepository: PeliculaRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val uiState: StateFlow<PeliculasUiState> = combine(
        peliculaRepository.allPeliculas,
        userPreferencesRepository.userPreferencesFlow
    ) { peliculas, prefs ->
        val sortedPeliculas = if (prefs.sortByTitle) {
            peliculas.sortedBy { it.titulo }
        } else {
            peliculas.sortedByDescending { it.updatedAt }
        }
        PeliculasUiState(peliculas = sortedPeliculas, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PeliculasUiState(isLoading = true)
    )

    fun deletePelicula(pelicula: Pelicula) {
        viewModelScope.launch {
            peliculaRepository.delete(pelicula)
        }
    }

    fun toggleFavorite(pelicula: Pelicula) {
        viewModelScope.launch {
            peliculaRepository.toggleFavorite(pelicula.id, !pelicula.isFavorite)
        }
    }
    
    suspend fun getPeliculaById(id: Int): Pelicula? {
        return peliculaRepository.allPeliculas.first().find { it.id == id }
    }
    
    fun upsertPelicula(pelicula: Pelicula) {
        viewModelScope.launch {
            if (pelicula.id == 0) {
                peliculaRepository.insert(pelicula)
            } else {
                peliculaRepository.update(pelicula)
            }
        }
    }
}
