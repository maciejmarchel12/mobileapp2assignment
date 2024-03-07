package com.example.historicallandmarksplacemark.views.landmarklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.main.MainApp
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historicallandmarksplacemark.adapters.LandmarkAdapter
import com.example.historicallandmarksplacemark.adapters.LandmarkListener
import com.example.historicallandmarksplacemark.databinding.ActivityLandmarkListBinding
import com.example.historicallandmarksplacemark.models.LandmarkModel
import timber.log.Timber.i


class LandmarkListView : AppCompatActivity(), LandmarkListener {

    private var position: Int = 0
    lateinit var app: MainApp
    private lateinit var binding: ActivityLandmarkListBinding
    lateinit var presenter: LandmarkListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = LandmarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // binding.recyclerView.adapter = LandmarkAdapter(app.landmarks)
        // binding.recyclerView.adapter = LandmarkAdapter(app.landmarks.findAll(),this)

        //FOR SEARCH FUNCTIONALITY
/*        landmarkAdapter = LandmarkAdapter(app.landmarks.findAll(), this).apply {
            filteredLandmarks = app.landmarks.findAll()
        }

        binding.recyclerView.adapter = landmarkAdapter*/
        loadLandmarks()
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
                presenter.doSearch(newText.orEmpty())
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddLandmark() }
            R.id.item_map -> { presenter.doShowLandmarkMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLandmarkClick(landmark: LandmarkModel, pos: Int) {
            this.position = pos
            presenter.doEditLandmark(landmark, this.position)
    }


    override fun showLandmarks(landmarks: List<LandmarkModel>) {
        val adapter = LandmarkAdapter(landmarks, this)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadLandmarks() {
        binding.recyclerView.adapter = LandmarkAdapter(presenter.getLandmarks(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getLandmarks().size)
    }

    fun onDelete(position: Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

    //Search
    fun onSearch(query: String) {
        i("Search query: $query")
    }

}