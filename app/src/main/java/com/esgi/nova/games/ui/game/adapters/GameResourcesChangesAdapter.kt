package com.esgi.nova.games.ui.game.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ui.game.view_holders.GameResourceChangeViewHolder

class GameResourcesChangesAdapter(private val resources: List<IFileWrapper<IDetailedChoice.IChangeValueResource>>) :
    RecyclerView.Adapter<GameResourceChangeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResourceChangeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameResourceChangeViewHolder(
            inflater,
            parent
        )
    }


    override fun getItemCount(): Int = resources.size
    override fun onBindViewHolder(holder: GameResourceChangeViewHolder, position: Int) {
        val resource: IFileWrapper<IDetailedChoice.IChangeValueResource> = resources[position]
        holder.bind(resource)
    }
}