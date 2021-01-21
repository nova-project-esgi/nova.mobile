package com.esgi.nova.games.ui.game.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.ui.game.fragments.OnChoiceClicked

class ChoiceViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.choice_list_item, parent, false)), View.OnClickListener {

    private var onChoiceClicked: OnChoiceClicked? = null
    private var name: Button? = null
    private var choice: IDetailedChoice? = null
    init
    {
        name = itemView.findViewById(R.id.choice_btn)
    }

    fun bind(choice: IDetailedChoice, onChoiceClicked: OnChoiceClicked)
    {

        this.choice = choice
        this.onChoiceClicked = onChoiceClicked
        name?.text = choice.title
        name?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        choice?.let {
            this.onChoiceClicked?.clicked(it)
        }
    }


}