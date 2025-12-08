package com.example.act2ens_aprprimercontactoconjetpackcompose

import android.app.Application
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database.PeliculaDatabase
import com.example.act2ens_aprprimercontactoconjetpackcompose.logica.repositories.PeliculaRepository

class PeliculaApp : Application() {


    val database by lazy { PeliculaDatabase.getDatabase(this) }


    val repository by lazy { PeliculaRepository(database.peliculaDao()) }
}