package com.example.historicallandmarksplacemark.main

import android.app.Application
import com.example.historicallandmarksplacemark.models.LandmarkMemStore
import com.example.historicallandmarksplacemark.models.LandmarkModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val landmarks = ArrayList<LandmarkModel>()
    val landmarks = LandmarkMemStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Application Started")
    }
}
