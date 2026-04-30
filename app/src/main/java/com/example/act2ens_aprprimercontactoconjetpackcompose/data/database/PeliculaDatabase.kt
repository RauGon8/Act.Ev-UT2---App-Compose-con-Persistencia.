package com.example.act2ens_aprprimercontactoconjetpackcompose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PeliculaEntity::class], version = 1, exportSchema = false)
abstract class PeliculaDatabase : RoomDatabase() {
    abstract fun peliculaDao(): PeliculaDao

    companion object {
        @Volatile
        private var Instance: PeliculaDatabase? = null

        fun getDatabase(context: Context): PeliculaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PeliculaDatabase::class.java, "pelicula_database")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
