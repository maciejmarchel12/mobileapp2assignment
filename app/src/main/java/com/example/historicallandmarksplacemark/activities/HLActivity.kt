package com.example.historicallandmarksplacemark.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.toHtml
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.helpers.showImagePicker
import com.example.historicallandmarksplacemark.main.MainApp
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.example.historicallandmarksplacemark.models.Location
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class HLActivity : AppCompatActivity() {

    var edit = false
    private lateinit var binding: ActivityHlBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var landmark = LandmarkModel() //Model integration
    var location = Location(52.245696, -7.139102, 15f) //Location
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp
        i("Historical Landmark Activity started...")

        registerImagePickerCallback()
        registerMapCallback()

        //EDIT
        if (intent.hasExtra("landmark_edit")) {
            edit = true
            landmark = intent.extras?.getParcelable("landmark_edit")!!
            binding.landmarkTitle.setText(landmark.title)
            binding.description.setText(landmark.description)
            binding.landmarkPreserve.setText(landmark.preserve)
            binding.landmarkLink.setText(landmark.link)
            binding.timePeriod.setText(landmark.timePeriod.toString())
            Picasso.get()
                .load(landmark.image)
                .into(binding.landmarkImage)
            if (landmark.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_landmark_image)
            }
            binding.btnAdd.setText(R.string.save_landmark)
        }

        binding.btnAdd.setOnClickListener() {
            landmark.title = binding.landmarkTitle.text.toString()
            landmark.description = binding.description.text.toString()
            landmark.preserve = binding.landmarkPreserve.text.toString()
            landmark.link = binding.landmarkLink.text.toString()
            landmark.timePeriod = Integer.parseInt(binding.timePeriod.text.toString())

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
        //Map Button
        binding.chooseImage.setOnClickListener {
            i("Select image")
            showImagePicker(imageIntentLauncher,this)
        }

        //Location Button
        binding.landmarkLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (landmark.zoom != 0f) {
                location.lat =  landmark.lat
                location.lng = landmark.lng
                location.zoom = landmark.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        //OLD    i("add button pressed")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_landmark, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.landmarks.delete(landmark)
                finish()
            }
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }

    //Image
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            landmark.image = image

                            Picasso.get()
                                .load(landmark.image)
                                .into(binding.landmarkImage)
                            binding.chooseImage.setText(R.string.change_landmark_image)
                        } // end of if statement
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    //Map
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                            landmark.lat = location.lat
                            landmark.lng = location.lng
                            landmark.zoom = location.zoom
                        } // end of if statement
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}


