package com.example.act2ens_aprprimercontactoconjetpackcompose.logica.repositories

import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database.PeliculaDao
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database.PeliculaEntity
import kotlinx.coroutines.flow.Flow

class PeliculaRepository(private val peliculaDao: PeliculaDao) {


    val peliculas: Flow<List<PeliculaEntity>> = peliculaDao.obtenerTodas()


    suspend fun agregarPelicula(titulo: String, genero: String) {
        val nuevaPelicula = PeliculaEntity(
            titulo = titulo,
            genero = genero,
            isFavorite = false
        )
        peliculaDao.insertar(nuevaPelicula)
    }


    suspend fun actualizarPelicula(pelicula: PeliculaEntity) {
        peliculaDao.actualizar(pelicula)
    }


    suspend fun borrarPelicula(pelicula: PeliculaEntity) {
        peliculaDao.borrar(pelicula)
    }

    suspend fun toggleFavorito(pelicula: PeliculaEntity) {
        // Copiamos el objeto invirtiendo el valor de isFavorite
        val peliculaActualizada = pelicula.copy(isFavorite = !pelicula.isFavorite)
        peliculaDao.actualizar(peliculaActualizada)
    }
}