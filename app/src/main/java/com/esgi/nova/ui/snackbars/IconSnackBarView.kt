package com.esgi.nova.ui.snackbars

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.esgi.nova.R
import com.google.android.material.snackbar.ContentViewCallback

class IconSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    var layRoot: ConstraintLayout
    var iconIv: ImageView
    var messageTv: TextView

    init {
        inflate(context, R.layout.view_icon_snackbar, this)
        clipToPadding = false
        layRoot = findViewById(R.id.snack_constraint)
        messageTv = findViewById(R.id.message_tv)
        iconIv = findViewById(R.id.icon_iv)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

}