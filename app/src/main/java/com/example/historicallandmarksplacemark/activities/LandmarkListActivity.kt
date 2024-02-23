package com.example.historicallandmarksplacemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.main.MainApp

class LandmarkListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_list)
        app = application as MainApp
    }
}