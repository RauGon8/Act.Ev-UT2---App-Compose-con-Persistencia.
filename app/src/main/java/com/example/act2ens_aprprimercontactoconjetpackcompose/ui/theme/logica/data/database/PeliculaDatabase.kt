package com.example.act2ens_aprprimercontactoconjetpackcompose.logica.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PeliculaEntity::class], version = 2, exportSchema = false)
abstract class PeliculaDatabase : RoomDatabase() {

    abstract fun peliculaDao(): PeliculaDao

    companion object {
        @Volatile
        private var INSTANCE: PeliculaDatabase? = null

        fun getDatabase(context: Context): PeliculaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PeliculaDatabase::class.java,
                    "pelicula_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}