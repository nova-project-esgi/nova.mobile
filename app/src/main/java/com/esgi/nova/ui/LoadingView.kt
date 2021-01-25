package com.esgi.nova.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.esgi.nova.R
import com.google.android.material.snackbar.ContentViewCallback

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    var loaderFl: FrameLayout

    init {
        inflate(context, R.layout.view_loading, this)
        clipToPadding = false
        this.loaderFl = findViewById(R.id.loader)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

}