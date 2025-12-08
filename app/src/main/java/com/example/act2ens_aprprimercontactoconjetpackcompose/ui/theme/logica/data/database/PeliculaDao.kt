package com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaDao {

    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<PeliculaEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(pelicula: PeliculaEntity)


    @Delete
    suspend fun borrar(pelicula: PeliculaEntity)


    @Update
    suspend fun actualizar(pelicula: PeliculaEntity)
}