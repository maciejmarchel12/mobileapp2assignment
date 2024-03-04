package com.example.historicallandmarksplacemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historicallandmarksplacemark.databinding.CardLandmarkBinding
import com.example.historicallandmarksplacemark.models.PlacemarkModel

class LandmarkAdapter constructor(private var landmarks: List<PlacemarkModel>) :
    RecyclerView.Adapter<LandmarkAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLandmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val landmark = landmarks[holder.adapterPosition]
        holder.bind(landmark)
    }

    override fun getItemCount(): Int = landmarks.size

    class MainHolder(private val binding : CardLandmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(landmark: PlacemarkModel) {
            binding.landmarkTitle.text = landmark.title
            binding.description.text = landmark.description
        }
    }
}