package com.esgi.nova.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.ResourceViewHolder
import com.esgi.nova.games.infrastructure.dto.GameResourceView

class ResourcesAdapter(private val resources: List<GameResourceView>) : RecyclerView.Adapter<ResourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ResourceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val resource: GameResourceView = resources[position]
        holder.bind(resource)
    }

    override fun getItemCount(): Int = resources.size
}