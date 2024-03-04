package com.example.historicallandmarksplacemark.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.historicallandmarksplacemark.R

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_landmark_image.toString())
    intentLauncher.launch(chooseFile)
}