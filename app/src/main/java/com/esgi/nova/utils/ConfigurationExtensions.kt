package com.esgi.nova.utils

import android.content.res.Configuration
import androidx.recyclerview.widget.RecyclerView

val Configuration.recyclerViewOrientation: Int
    get() = if (orientation == Configuration.ORIENTATION_LANDSCAPE) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL