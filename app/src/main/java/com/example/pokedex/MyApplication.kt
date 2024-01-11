package com.example.pokedex

import android.app.Application
import com.example.pokedex.di.AppComponent
import com.example.pokedex.di.DaggerAppComponent
import com.example.pokedex.prefdatastore.DataStoreManager

open class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    open val dataStoreManager by lazy {
        DataStoreManager(this)
    }
}