package com.example.pokedex

import android.app.Application
import com.example.pokedex.di.AppComponent
import com.example.pokedex.di.DaggerAppComponent

open class MyApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}