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
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val showFavoritesOnly: Boolean = false
)

class PeliculasViewModel(
    private val peliculaRepository: PeliculaRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Estados de filtro locales
    private val _searchQuery = MutableStateFlow("")
    private val _showFavoritesOnly = MutableStateFlow(false)

    val uiState: StateFlow<PeliculasUiState> = combine(
        peliculaRepository.allPeliculas,
        userPreferencesRepository.userPreferencesFlow,
        _searchQuery,
        _showFavoritesOnly
    ) { peliculas, prefs, query, favOnly ->
        // 1. Ordenar según preferencia del usuario
        val sorted = if (prefs.sortByTitle) {
            peliculas.sortedBy { it.titulo.lowercase() }
        } else {
            peliculas.sortedByDescending { it.updatedAt }
        }
        // 2. Filtrar por favoritos si está activo
        val filtered = if (favOnly) sorted.filter { it.isFavorite } else sorted
        // 3. Filtrar por texto de búsqueda
        val searched = if (query.isBlank()) filtered else {
            filtered.filter {
                it.titulo.contains(query, ignoreCase = true) ||
                it.genero.contains(query, ignoreCase = true)
            }
        }
        PeliculasUiState(
            peliculas = searched,
            isLoading = false,
            searchQuery = query,
            showFavoritesOnly = favOnly
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PeliculasUiState(isLoading = true)
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun toggleShowFavoritesOnly() {
        _showFavoritesOnly.value = !_showFavoritesOnly.value
    }

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
        return peliculaRepository.getById(id)
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
