package com.esgi.nova.ui.dashboard.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.infrastructure.ports.IFileWrapper

class DifficultyResourceViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(
        R.layout.difficulty_resource_item, parent, false)) {

    private var icon: ImageView? = null
    private var name: TextView? = null
    private var startValue: TextView? = null

    init {
        name = itemView.findViewById(R.id.resource_name_tv)
        icon = itemView.findViewById(R.id.resource_icon_iv)
        startValue = itemView.findViewById(R.id.resource_value_tv)
    }

    fun bind(resource: IFileWrapper<IDetailedDifficulty.IStartValueResource>) {
        name?.text = resource.data.name
        startValue?.text = resource.data.startValue.toString()
        icon?.setImageBitmap(resource.file)
    }

}