package com.example.historicallandmarksplacemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.main.MainApp
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i
import com.example.historicallandmarksplacemark.models.LandmarkModel

@Suppress("DEPRECATION")
class HLActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHlBinding
    var landmark = LandmarkModel() //Model integration
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityHlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Historical Landmark Activity started...")

        //EDIT
        if (intent.hasExtra("landmark_edit")) {
            edit = true
            landmark = intent.extras?.getParcelable("landmark_edit")!!
            binding.landmarkTitle.setText(landmark.title)
            binding.description.setText(landmark.description)
            binding.btnAdd.setText(R.string.save_landmark)
        }

        binding.btnAdd.setOnClickListener() {
            landmark.title = binding.landmarkTitle.text.toString()
            landmark.description = binding.description.text.toString()

            if (landmark.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_landmark_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.landmarks.update(landmark.copy())
                } else {
                    app.landmarks.create(landmark.copy())
                }
                setResult(RESULT_OK)
                finish()
            }
        }
        //OLD    i("add button pressed")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_landmark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}


