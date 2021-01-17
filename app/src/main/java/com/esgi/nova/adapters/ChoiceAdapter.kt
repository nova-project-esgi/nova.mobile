package com.esgi.nova.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.ChoiceViewHolder
import com.esgi.nova.events.ports.IDetailedChoice

class ChoiceAdapter(private val choices: List<IDetailedChoice>) : RecyclerView.Adapter<ChoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChoiceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        val choice: IDetailedChoice = choices[position]
        holder.bind(choice)
    }

    override fun getItemCount(): Int = choices.size
}