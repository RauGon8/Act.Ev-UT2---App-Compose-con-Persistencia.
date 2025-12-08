package com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peliculas")
data class PeliculaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val genero: String,
    val isFavorite: Boolean = false
)