package com.example.historicallandmarksplacemark.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.historicallandmarksplacemark.databinding.ActivityLandmarkMapsBinding
import com.example.historicallandmarksplacemark.databinding.ContentLandmarkMapsBinding
import com.example.historicallandmarksplacemark.main.MainApp
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class LandmarkMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityLandmarkMapsBinding
    private lateinit var contentBinding: ContentLandmarkMapsBinding
    lateinit var presenter: LandmarkMapPresenter
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityLandmarkMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = LandmarkMapPresenter(this)

        contentBinding = ContentLandmarkMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }
    }

    fun showLandmark(landmark: LandmarkModel) {
        contentBinding.currentTitle.text = landmark.title
        contentBinding.currentDescription.text = landmark.description
        contentBinding.currentLink.text = landmark.link
        contentBinding.currentPreserve.text = landmark.preserve
        contentBinding.currentTimePeriod.text = landmark.timePeriod.toString()
        Picasso.get()
            .load(landmark.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //val landmark = marker.tag as LandmarkModel
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

}