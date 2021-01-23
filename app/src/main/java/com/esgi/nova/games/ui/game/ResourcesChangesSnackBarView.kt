package com.esgi.nova.games.ui.game

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.google.android.material.snackbar.ContentViewCallback

class ResourcesChangesSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    var resourcesRv: RecyclerView
    var layRoot: ConstraintLayout

    init {
        View.inflate(context, R.layout.view_resources_changes_snackbar, this)
        clipToPadding = false
        this.layRoot = findViewById(R.id.snack_constraint)
        this.resourcesRv = findViewById(R.id.resources_rv)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        TODO("Not yet implemented")
    }

}