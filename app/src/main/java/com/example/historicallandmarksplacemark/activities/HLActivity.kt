package com.example.historicallandmarksplacemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.main.MainApp
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.i
import com.example.historicallandmarksplacemark.models.PlacemarkModel

class HLActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHlBinding
    var placemark = PlacemarkModel() //Model integration
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHlBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Timber.plant(Timber.DebugTree())
//        i("Historical Landmark Activity started...")

        app = application as MainApp
        i("Historical Landmark Activity started...")
        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.landmarkTitle.text.toString()
            placemark.description = binding.description.text.toString()
            if (placemark.title.isNotEmpty()) {
                app.placemarks.add(placemark.copy())
                i("add button pressed: $placemark.title")
                for (i in app.placemarks.indices)
                    { i("Placemark[$i]:${this.app.placemarks[i]}")}
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        //OLD    i("add button pressed")
        }
    }
}