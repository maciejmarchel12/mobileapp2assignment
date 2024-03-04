package com.example.historicallandmarksplacemark.main

import android.app.Application
import com.example.historicallandmarksplacemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val landmarks = ArrayList<PlacemarkModel>()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Application Started")
    }
}
