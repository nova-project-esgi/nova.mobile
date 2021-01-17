package com.esgi.nova

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.events.ports.IDetailedChoice

class ChoiceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.choice_list_item, parent, false)) {


    private var name: Button? = null

    init
    {
        name = itemView.findViewById(R.id.choice_button)
    }

    fun bind(choice: IDetailedChoice)
    {
        name?.text = choice.title
    }
}