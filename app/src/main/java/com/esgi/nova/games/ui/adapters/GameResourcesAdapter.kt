package com.esgi.nova.games.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.games.ui.view_holders.GameResourceViewHolder

class GameResourcesAdapter(private val resources: List<IFileWrapper<ITotalValueResource>>) :
    RecyclerView.Adapter<GameResourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameResourceViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holderGame: GameResourceViewHolder, position: Int) {
        val resource: IFileWrapper<ITotalValueResource> = resources[position]
        holderGame.bind(resource)
    }

    override fun getItemCount(): Int = resources.size
}


