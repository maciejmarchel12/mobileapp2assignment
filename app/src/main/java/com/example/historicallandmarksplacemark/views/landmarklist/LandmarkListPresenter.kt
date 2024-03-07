package com.example.historicallandmarksplacemark.views.landmarklist

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.historicallandmarksplacemark.activities.LandmarkMapsActivity
import com.example.historicallandmarksplacemark.main.MainApp
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.example.historicallandmarksplacemark.views.landmark.LandmarkView

class LandmarkListPresenter(val view: LandmarkListView) {

    var app: MainApp
    private lateinit var refresIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var allLandmarks: List<LandmarkModel>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
        loadLandmarks()
    }

    fun getLandmarks() = app.landmarks.findAll()

    fun doAddLandmark() {
        val launcherIntent = Intent(view, LandmarkView::class.java)
        refresIntentLauncher.launch(launcherIntent)
    }

    fun doEditLandmark(landmark: LandmarkModel, pos: Int) {
        val launcherIntent = Intent(view, LandmarkView::class.java)
        launcherIntent.putExtra("landmark_edit", landmark)
        position = pos
        refresIntentLauncher.launch(launcherIntent)
    }

    fun doShowLandmarkMap() {
        val launcherIntent = Intent(view, LandmarkMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    //Search

    fun loadLandmarks() {
        allLandmarks = app.landmarks.findAll()
        view.showLandmarks(allLandmarks)
    }

    fun doSearch(query: String) {
        val filteredLandmarks = if (query.isEmpty()) {
            allLandmarks
        } else {
            allLandmarks.filter { landmark ->
                landmark.title.contains(query, ignoreCase = true) ||
                        landmark.description.contains(query, ignoreCase = true) ||
                        landmark.preserve.contains(query, ignoreCase = true) ||
                        landmark.link.contains(query, ignoreCase = true) ||
                        landmark.timePeriod.toString().contains(query, ignoreCase = true)
            }
        }
        view.showLandmarks(filteredLandmarks)
    }

    private fun registerRefreshCallback() {
        refresIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else //Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }
    }


}