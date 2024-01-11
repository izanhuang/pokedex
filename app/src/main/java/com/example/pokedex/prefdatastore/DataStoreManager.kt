package com.example.pokedex.prefdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokedex.types.PokedexDetail
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

const val POKEDEX_DATASTORE = "pokedex_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = POKEDEX_DATASTORE)

@Singleton
class DataStoreManager @Inject constructor(private val context: Context) {
    companion object {
        val GENERATION_ID = stringPreferencesKey("GENERATION_ID")
    }

    suspend fun saveToDataStore(pokedexDetail: PokedexDetail) {
        context.dataStore.edit {
            it[GENERATION_ID] = pokedexDetail.generationId
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        PokedexDetail(
            generationId = it[GENERATION_ID]?:""
        )
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}