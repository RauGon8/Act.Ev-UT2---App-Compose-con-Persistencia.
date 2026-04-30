package com.example.act2ens_aprprimercontactoconjetpackcompose

import android.content.Context
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.database.PeliculaDatabase
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore.UserPreferencesRepository
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore.dataStore
import com.example.act2ens_aprprimercontactoconjetpackcompose.data.repository.PeliculaRepository

interface AppContainer {
    val peliculaRepository: PeliculaRepository
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val peliculaRepository: PeliculaRepository by lazy {
        PeliculaRepository(PeliculaDatabase.getDatabase(context).peliculaDao())
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }
}
