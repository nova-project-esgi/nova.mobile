package com.esgi.nova.games.ui.game.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.ui.game.fragments.OnChoiceClicked
import com.esgi.nova.games.ui.game.view_holders.ChoiceViewHolder

class ChoiceAdapter(
    private val choices: List<IDetailedChoice>,
    private val choiceClickedListener: OnChoiceClicked
) : RecyclerView.Adapter<ChoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChoiceViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: ChoiceViewHolder, position: Int) {
        val choice: IDetailedChoice = choices[position]
        holder.bind(choice, choiceClickedListener)

    }

    override fun getItemCount(): Int = choices.size
}