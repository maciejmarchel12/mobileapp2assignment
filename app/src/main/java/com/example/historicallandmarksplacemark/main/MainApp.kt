package com.example.historicallandmarksplacemark.main

import android.app.Application
import com.example.historicallandmarksplacemark.models.LandmarkJSONStore
import com.example.historicallandmarksplacemark.models.LandmarkMemStore
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.example.historicallandmarksplacemark.models.LandmarkStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

   // val landmarks = ArrayList<LandmarkModel>()
   // val landmarks = LandmarkMemStore()
    lateinit var landmarks : LandmarkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // To switch between storage types:
        // landmarks = LandmarkMemStore()
        landmarks = LandmarkJSONStore(applicationContext)
        i("Application Started")
    }
}
