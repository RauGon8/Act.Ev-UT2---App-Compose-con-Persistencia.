package com.example.act2ens_aprprimercontactoconjetpackcompose.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class UserPreferences(
    val isDarkMode: Boolean,
    val sortByTitle: Boolean
)

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val SORT_BY_TITLE = booleanPreferencesKey("sort_by_title")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserPreferences(
                isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] ?: false,
                sortByTitle = preferences[PreferencesKeys.SORT_BY_TITLE] ?: true
            )
        }

    suspend fun updateDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_MODE] = isDarkMode
        }
    }

    suspend fun updateSortByTitle(sortByTitle: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_BY_TITLE] = sortByTitle
        }
    }
}
