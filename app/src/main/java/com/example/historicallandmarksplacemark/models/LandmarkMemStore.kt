package com.example.historicallandmarksplacemark.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
class LandmarkMemStore : LandmarkStore {

    val landmarks: ArrayList<LandmarkModel> = ArrayList()

    override fun findAll(): List<LandmarkModel> {
        return landmarks
    }

    override fun create(landmark: LandmarkModel) {
        landmark.id = getId()
        landmarks.add(landmark)
        logAll()
    }

    override fun update(landmark: LandmarkModel) {
        var foundLandmark: LandmarkModel? = landmarks.find { l -> l.id == landmark.id }
        if (foundLandmark != null) {
            foundLandmark.title = landmark.title
            foundLandmark.description = landmark.description
            logAll()
        }
    }

    fun logAll() {
        landmarks.forEach { i("${it}") }
    }
}