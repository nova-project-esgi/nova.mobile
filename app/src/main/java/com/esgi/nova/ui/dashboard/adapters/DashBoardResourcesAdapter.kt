package com.esgi.nova.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.ui.dashboard.view_holders.DifficultyResourceViewHolder

class DashBoardResourcesAdapter(private val resources: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>) :
    RecyclerView.Adapter<DifficultyResourceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DifficultyResourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DifficultyResourceViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holderGame: DifficultyResourceViewHolder, position: Int) {
        val resource: IFileWrapper<IDetailedDifficulty.IStartValueResource> = resources[position]
        holderGame.bind(resource)
    }

    override fun getItemCount(): Int = resources.size
}