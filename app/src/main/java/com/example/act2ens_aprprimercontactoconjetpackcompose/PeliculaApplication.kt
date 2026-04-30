package com.example.act2ens_aprprimercontactoconjetpackcompose

import android.app.Application

class PeliculaApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
