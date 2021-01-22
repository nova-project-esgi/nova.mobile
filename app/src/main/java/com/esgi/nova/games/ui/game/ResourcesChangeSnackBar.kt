package com.esgi.nova.games.ui.game

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ui.game.adapters.GameResourcesChangesAdapter
import com.esgi.nova.utils.findSuitableParent
import com.esgi.nova.utils.recyclerViewOrientation
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.android.synthetic.main.view_resources_changes_snackbar.view.*

class ResourcesChangeSnackBar(
    parent: ViewGroup,
    content: ResourcesChangesSnackBarView
) : BaseTransientBottomBar<ResourcesChangeSnackBar>(parent, content, content) {


    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            view: View,
            resourcesChanges: List<IFileWrapper<IDetailedChoice.IChangeValueResource>>,
            duration: Int
        ): ResourcesChangeSnackBar? {

            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            try {
                val customView = LayoutInflater.from(view.context).inflate(
                    R.layout.layout_resources_changes_snackbar,
                    parent,
                    false
                ) as ResourcesChangesSnackBarView
                customView.resources_rv?.apply {
                    layoutManager = LinearLayoutManager(
                        context,
                        RecyclerView.HORIZONTAL,
                        false
                    )
                    adapter =
                        GameResourcesChangesAdapter(
                            resourcesChanges
                        )
                }
                return ResourcesChangeSnackBar(
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