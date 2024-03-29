package com.example.historicallandmarksplacemark.views.landmarklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historicallandmarksplacemark.databinding.CardLandmarkBinding
import com.example.historicallandmarksplacemark.models.LandmarkModel
import com.squareup.picasso.Picasso

interface LandmarkListener {
    fun onLandmarkClick(landmark: LandmarkModel, position: Int)
    fun showLandmarks(allLandmarks: List<LandmarkModel>)
}

class LandmarkAdapter constructor(private var landmarks: List<LandmarkModel>,
                                  private val listener: LandmarkListener
) :
    RecyclerView.Adapter<LandmarkAdapter.MainHolder>() {

    //Search
    var filteredLandmarks : List<LandmarkModel> = landmarks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardLandmarkBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

/*    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val landmark = landmarks[holder.adapterPosition]
        holder.bind(landmark, listener)
    }*/

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        if (position in 0 until filteredLandmarks.size) {
            val landmark = filteredLandmarks[position]
            holder.bind(landmark, listener)
        }
    }

    override fun getItemCount(): Int = landmarks.size

    class MainHolder(private val binding : CardLandmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(landmark: LandmarkModel, listener: LandmarkListener) {
            binding.landmarkTitle.text = landmark.title
            binding.description.text = landmark.description
            binding.landmarkPreserve.text = landmark.preserve
            binding.landmarkLink.text = landmark.link
            binding.timePeriod.setText(landmark.timePeriod.toString())
            Picasso.get().load(landmark.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onLandmarkClick(landmark,adapterPosition)}
        }
    }

    //Search Filter
    fun filter(query: String) {
        filteredLandmarks = landmarks.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.preserve.contains(query, ignoreCase = true) ||
                    it.link.contains(query, ignoreCase = true) ||
                    it.timePeriod.toString().contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }
}