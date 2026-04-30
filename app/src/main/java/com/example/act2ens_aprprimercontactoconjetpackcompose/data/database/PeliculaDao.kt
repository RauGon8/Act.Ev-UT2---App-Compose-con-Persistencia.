package com.example.act2ens_aprprimercontactoconjetpackcompose.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaDao {
    @Query("SELECT * FROM peliculas ORDER BY updatedAt DESC")
    fun getAllPeliculas(): Flow<List<PeliculaEntity>>

    @Query("SELECT * FROM peliculas WHERE id = :id")
    suspend fun getPeliculaById(id: Int): PeliculaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPelicula(pelicula: PeliculaEntity)

    @Update
    suspend fun updatePelicula(pelicula: PeliculaEntity)

    @Delete
    suspend fun deletePelicula(pelicula: PeliculaEntity)

    @Query("UPDATE peliculas SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}
