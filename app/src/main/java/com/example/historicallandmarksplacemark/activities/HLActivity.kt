package com.example.historicallandmarksplacemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.main.MainApp
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.i
import com.example.historicallandmarksplacemark.models.PlacemarkModel

class HLActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHlBinding
    var landmark = PlacemarkModel() //Model integration
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
//        Timber.plant(Timber.DebugTree())
//        i("Historical Landmark Activity started...")

        app = application as MainApp
        i("Historical Landmark Activity started...")

        binding.btnAdd.setOnClickListener() {
            landmark.title = binding.landmarkTitle.text.toString()
            landmark.description = binding.description.text.toString()
            if (landmark.title.isNotEmpty()) {
                app.landmarks.add(landmark.copy())
                i("add button pressed: $landmark.title")
                for (i in app.landmarks.indices)
                    { i("Landmark[$i]:${this.app.landmarks[i]}")

                        setResult(RESULT_OK)
                        finish()
                }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        //OLD    i("add button pressed")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_landmark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}