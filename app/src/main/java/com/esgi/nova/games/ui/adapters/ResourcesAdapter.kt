package com.esgi.nova.games.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ui.view_holders.ResourceViewHolder
import com.esgi.nova.games.ports.ITotalValueResource

class ResourcesAdapter(private val resources: List<IFileWrapper<ITotalValueResource>>) : RecyclerView.Adapter<ResourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ResourceViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        val resource: IFileWrapper<ITotalValueResource> = resources[position]
        holder.bind(resource)
    }

    override fun getItemCount(): Int = resources.size
}