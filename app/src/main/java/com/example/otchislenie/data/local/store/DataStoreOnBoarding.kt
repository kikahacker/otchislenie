package com.example.otchislenie.data.local.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreOnBoarding(private val context: Context) {
    private val onBoardingKey = booleanPreferencesKey("onboarding")
    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun setOnBoardingCompleted(completed: Boolean) {
        context.datastore.edit { preferences ->
            preferences[onBoardingKey] = completed
        }
    }

    val onBoardingCompleted: Flow<Boolean> = context.datastore.data
        .map { preferences ->
            preferences[onBoardingKey] ?: false
        }
}