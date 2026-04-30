package com.example.act2ens_aprprimercontactoconjetpackcompose.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "peliculas",
    indices = [Index(value = ["updatedAt"])]
)
data class PeliculaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val genero: String,
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
