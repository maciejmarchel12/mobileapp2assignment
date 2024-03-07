package com.example.historicallandmarksplacemark.views.landmark

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.helpers.showImagePicker
import com.example.historicallandmarksplacemark.main.MainApp
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.example.historicallandmarksplacemark.models.Location
import com.example.historicallandmarksplacemark.views.editlocation.EditLocationView
import timber.log.Timber

@Suppress("DEPRECATION")
class LandmarkPresenter(private val view: LandmarkView) {

    var landmark = LandmarkModel() //Model integration
    var app: MainApp = view.application as MainApp
    var binding: ActivityHlBinding = ActivityHlBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("landmark_edit")) {
            edit = true
            landmark = view.intent.extras?.getParcelable("landmark_edit")!!
            view.showLandmark(landmark)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String, preserve: String, link: String, timePeriod: Int) {
        landmark.title = title
        landmark.description = description
        landmark.preserve = preserve
        landmark.link = link
        landmark.timePeriod = timePeriod

        if (edit) {
            app.landmarks.update(landmark)
        } else {
            app.landmarks.create(landmark)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.landmarks.delete(landmark)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (landmark.zoom != 0f) {
            location.lat =  landmark.lat
            location.lng = landmark.lng
            location.zoom = landmark.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheLandmark (title: String, description: String, preserve: String, link: String, timePeriod: Int) {
        landmark.title = title;
        landmark.description = description;
        landmark.preserve = preserve;
        landmark.link = link;
        landmark.timePeriod = timePeriod
    }

    //Image
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            landmark.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(landmark.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(landmark.image)
                        } // end of if statement
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    //Map
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            landmark.lat = location.lat
                            landmark.lng = location.lng
                            landmark.zoom = location.zoom
                        } // end of if statement
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}