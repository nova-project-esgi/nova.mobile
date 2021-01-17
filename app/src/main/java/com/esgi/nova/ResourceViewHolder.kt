package com.esgi.nova

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.games.infrastructure.dto.GameResourceView
import com.esgi.nova.games.ports.ITotalValueResource

class ResourceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.resource_list_item, parent, false))
{

    private var name: TextView? = null
    private var amount: TextView? = null


    init
    {
        name = itemView.findViewById(R.id.name)
        amount = itemView.findViewById(R.id.amount)
    }

    fun bind(resource: ITotalValueResource)
    {
        name?.text = resource.name
        amount?.text = resource.total.toString()
    }

}
