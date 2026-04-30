package com.example.act2ens_aprprimercontactoconjetpackcompose.domain.model

data class Pelicula(
    val id: Int = 0,
    val titulo: String,
    val genero: String,
    val isFavorite: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis()
)
