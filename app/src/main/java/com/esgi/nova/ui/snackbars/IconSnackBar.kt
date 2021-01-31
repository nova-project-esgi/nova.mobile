package com.esgi.nova.ui.snackbars

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.esgi.nova.R
import com.esgi.nova.games.ui.game.ResourcesChangeSnackBar
import com.esgi.nova.utils.findSuitableParent
import com.google.android.material.snackbar.BaseTransientBottomBar
import org.jetbrains.anko.imageResource

class IconSnackBar(
    parent: ViewGroup,
    content: IconSnackBarView
) : BaseTransientBottomBar<IconSnackBar>(parent, content, content) {


    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }
    companion object{
        fun View.networkErrorSnackBar(
            duration: Int = 2000
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_signal_wifi_off_red_700_24dp, this.context.getString(R.string.network_not_available_msg), duration)
        }
        fun View.unexpectedErrorSnackBar(
            duration: Int = 2000
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_error_outline_red_700_24dp, this.context.getString(R.string.unexpected_error_msg), duration)
        }
        fun View.errorSnackBar(
            messageId: Int,
            duration: Int = 2000,
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_error_outline_red_700_24dp, this.context.getString(messageId), duration)
        }
        fun View.warnSnackBar(
            messageId: Int,
            duration: Int = 2000,
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_warning_amber_400_24dp, this.context.getString(messageId), duration)
        }
        fun View.infoSnackBar(
            messageId: Int,
            duration: Int = 2000
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_feedback_black_24dp, this.context.getString(messageId), duration)
        }
        fun View.confirmSnackBar(
            messageId: Int,
            duration: Int = 2000
        ): IconSnackBar? {
            return getIconSnackBar(R.drawable.baseline_check_circle_outline_green_600_24dp, this.context.getString(messageId), duration)
        }
        fun View.iconSnackBar(
            iconResourceId: Int,
            message: String,
            duration: Int
        ): IconSnackBar? {

            return getIconSnackBar(iconResourceId, message, duration)
        }

        fun View.iconSnackBar(
            iconResourceId: Int,
            messageId: Int,
            duration: Int
        ): IconSnackBar? {
            return getIconSnackBar(iconResourceId, this.context.getString(messageId), duration)
        }

        private fun View.getIconSnackBar(
            iconResourceId: Int,
            message: String,
            duration: Int
        ): IconSnackBar? {
            val parent = this.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )
            try {
                val customView = LayoutInflater.from(this.context).inflate(
                    R.layout.layout_icon_snackbar,
                    parent,
                    false
                ) as IconSnackBarView
                customView.iconIv.imageResource = iconResourceId
                customView.messageTv.text = message
                return IconSnackBar(
                    parent,
                    customView
                ).setDuration(duration)
            } catch (e: Exception) {
                e.message?.let { Log.v("exception ", it) }
            }
            return null
        }
    }
}