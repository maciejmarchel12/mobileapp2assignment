package com.example.historicallandmarksplacemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historicallandmarksplacemark.databinding.CardLandmarkBinding
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.squareup.picasso.Picasso

interface LandmarkListener {
    fun onLandmarkClick(landmark: LandmarkModel, position: Int)
}

class LandmarkAdapter constructor(private var landmarks: List<LandmarkModel>,
                                  private val listener: LandmarkListener) :
    RecyclerView.Adapter<LandmarkAdapter.MainHolder>() {

    private var originalLandmarks: MutableList<LandmarkModel> = landmarks.toMutableList()
    private var filteredLandmarks: MutableList<LandmarkModel> = originalLandmarks

    //Search Function
    fun filter(query: String) {
        filteredLandmarks = if (query.isBlank()) {
            originalLandmarks
        } else {
            originalLandmarks.filter {
                it.title.contains(query, ignoreCase = true) || it.description.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLandmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val landmark = landmarks[holder.adapterPosition]
        holder.bind(landmark, listener)
    }

    override fun getItemCount(): Int = filteredLandmarks.size

    class MainHolder(private val binding : CardLandmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(landmark: LandmarkModel, listener: LandmarkListener) {
            binding.landmarkTitle.text = landmark.title
            binding.description.text = landmark.description
            Picasso.get().load(landmark.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onLandmarkClick(landmark,adapterPosition)}
        }
    }
}