package com.esgi.nova.games.ui.game.view_holders

import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.utils.toChangeString
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource

class GameResourceChangeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.game_resource_change_item, parent, false)) {

    private var iconIv: ImageView? = null
    private var changeValueTv: TextView? = null
    private var changeIconIv: ImageView? = null

    init {
        changeValueTv = itemView.findViewById(R.id.change_amount_tv)
        iconIv = itemView.findViewById(R.id.resource_icon_iv)
        changeIconIv = itemView.findViewById(R.id.change_icon_iv)
    }

    fun bind(resource: IFileWrapper<IDetailedChoice.IChangeValueResource>) {
        changeValueTv?.text = resource.data.changeValue.toChangeString()
        if (resource.data.changeValue >= 0) {
            changeValueTv?.textColorResource = R.color.primaryGreenColor
            changeIconIv?.imageResource = R.drawable.baseline_keyboard_arrow_up_green_700_24dp
        } else {
            changeValueTv?.textColorResource = R.color.primaryRedColor
            changeIconIv?.imageResource = R.drawable.baseline_keyboard_arrow_down_red_900_24dp
        }
        iconIv?.setImageBitmap(resource.file)
    }

}