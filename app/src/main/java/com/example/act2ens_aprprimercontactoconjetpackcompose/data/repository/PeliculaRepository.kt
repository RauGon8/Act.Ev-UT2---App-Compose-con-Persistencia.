package com.example.act2ens_aprprimercontactoconjetpackcompose.data.repository

import com.example.act2ens_aprprimercontactoconjetpackcompose.data.database.PeliculaDao
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.database.PeliculaEntity
import com.example.act2ens_aprprimercontactoconjetpackcompose.domain.model.Pelicula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PeliculaRepository(private val peliculaDao: PeliculaDao) {

    val allPeliculas: Flow<List<Pelicula>> = peliculaDao.getAllPeliculas().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun insert(pelicula: Pelicula) {
        peliculaDao.insertPelicula(pelicula.toEntity())
    }

    suspend fun update(pelicula: Pelicula) {
        peliculaDao.updatePelicula(pelicula.toEntity())
    }

    suspend fun delete(pelicula: Pelicula) {
        peliculaDao.deletePelicula(pelicula.toEntity())
    }

    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        peliculaDao.updateFavoriteStatus(id, isFavorite)
    }

    private fun PeliculaEntity.toDomain(): Pelicula = Pelicula(
        id = id,
        titulo = titulo,
        genero = genero,
        isFavorite = isFavorite,
        updatedAt = updatedAt
    )

    private fun Pelicula.toEntity(): PeliculaEntity = PeliculaEntity(
        id = id,
        titulo = titulo,
        genero = genero,
        isFavorite = isFavorite,
        updatedAt = updatedAt
    )
}
