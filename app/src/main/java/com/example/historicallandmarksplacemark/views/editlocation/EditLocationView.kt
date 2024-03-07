package com.example.historicallandmarksplacemark.views.editlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.historicallandmarksplacemark.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.historicallandmarksplacemark.models.Location
import com.google.android.gms.maps.model.Marker

@Suppress("DEPRECATION")
class EditLocationView : AppCompatActivity(), OnMapReadyCallback,
                                GoogleMap.OnMarkerDragListener,
                                GoogleMap.OnMarkerClickListener{

    private lateinit var map: GoogleMap
    lateinit var presenter: EditLocationPresenter
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = EditLocationPresenter(this)
        setContentView(R.layout.activity_map)
        location = intent.extras?.getParcelable<Location>("location")!!
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        presenter.initMap(map)
    }

    override fun onMarkerDrag(marker: Marker) {

    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, map.cameraPosition.zoom)
    }

    override fun onMarkerDragStart(marker: Marker) {

    }

    override fun onBackPressed() {
        presenter.doOnBackPressed()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }
}