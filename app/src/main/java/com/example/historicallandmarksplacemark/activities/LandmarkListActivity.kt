package com.example.historicallandmarksplacemark.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.main.MainApp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historicallandmarksplacemark.adapters.LandmarkAdapter
import com.example.historicallandmarksplacemark.adapters.LandmarkListener
import com.example.historicallandmarksplacemark.databinding.ActivityLandmarkListBinding
import com.example.historicallandmarksplacemark.models.LandmarkModel


class LandmarkListActivity : AppCompatActivity(), LandmarkListener {

    private var position: Int = 0
    lateinit var app: MainApp
    private lateinit var binding: ActivityLandmarkListBinding
    private lateinit var landmarkAdapter: LandmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // binding.recyclerView.adapter = LandmarkAdapter(app.landmarks)
        // binding.recyclerView.adapter = LandmarkAdapter(app.landmarks.findAll(),this)

        landmarkAdapter = LandmarkAdapter(app.landmarks.findAll(), this).apply {
            filteredLandmarks = app.landmarks.findAll()
        }

        binding.recyclerView.adapter = landmarkAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                landmarkAdapter.filter(newText.orEmpty())
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, HLActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, LandmarkMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                        notifyItemRangeChanged(0,app.landmarks.findAll().size)
            }
        }

    override fun onLandmarkClick(landmark: LandmarkModel, pos: Int) {
        val launcherIntent = Intent(this, HLActivity::class.java)
        launcherIntent.putExtra("landmark_edit", landmark)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                        notifyItemRangeChanged(0,app.landmarks.findAll().size)
            }
            else //deleting
                if (it.resultCode == 99) (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
}