package com.example.historicallandmarksplacemark.views.landmark

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.databinding.ActivityHlBinding
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.i

@Suppress("DEPRECATION")
class LandmarkView : AppCompatActivity() {


    private lateinit var binding: ActivityHlBinding
    private lateinit var presenter: LandmarkPresenter
    var landmark = LandmarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = LandmarkPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheLandmark(binding.landmarkLink.text.toString(), binding.description.text.toString(),
            binding.landmarkPreserve.text.toString(), binding.landmarkLink.text.toString(), binding.timePeriod.text.toString().toInt())
            presenter.doSelectImage()
        }

        binding.landmarkLocation.setOnClickListener {
            presenter.cacheLandmark(binding.landmarkLink.text.toString(), binding.description.text.toString(),
                binding.landmarkPreserve.text.toString(), binding.landmarkLink.text.toString(), binding.timePeriod.text.toString().toInt())
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.landmarkTitle.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_landmark_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddOrSave(
                    binding.landmarkTitle.text.toString(),
                    binding.description.text.toString(),
                    binding.landmarkPreserve.text.toString(),
                    binding.landmarkLink.text.toString(),
                    binding.timePeriod.text.toString().toInt()
                )
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_landmark, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLandmark(landmark: LandmarkModel) {
        binding.landmarkTitle.setText(landmark.title)
        binding.description.setText(landmark.description)
        binding.landmarkPreserve.setText(landmark.preserve)
        binding.landmarkLink.setText(landmark.link)
        binding.timePeriod.setText(landmark.timePeriod.toString())
        binding.btnAdd.setText(R.string.save_landmark)
        Picasso.get()
            .load(landmark.image)
            .into(binding.landmarkImage)
        if (landmark.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_landmark_image)
        }
    }

    fun updateImage(image: Uri) {
        i("Image Updated")
        Picasso.get()
            .load(image)
            .into(binding.landmarkImage)
        binding.chooseImage.setText(R.string.change_landmark_image)
    }

}


