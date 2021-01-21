package com.esgi.nova.games.ui.game.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.ITotalValueResource

class GameResourceViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.game_resource_item, parent, false)) {

    private var icon: ImageView? = null
    private var amount: TextView? = null


    init {
        amount = itemView.findViewById(R.id.amount)
        icon = itemView.findViewById(R.id.resource_icon_iv)
    }

    fun bind(resource: IFileWrapper<ITotalValueResource>) {
        amount?.text = resource.data.total.toString()
        icon?.setImageBitmap(resource.img)
    }

}

