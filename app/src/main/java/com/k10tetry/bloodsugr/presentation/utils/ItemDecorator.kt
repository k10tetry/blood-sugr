package com.k10tetry.bloodsugr.presentation.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ItemDecorator @Inject constructor() : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val margin = 16F.toPx(parent.resources).toInt()
        val position = parent.getChildAdapterPosition(view) + 1
        val bottom = if (position == parent.adapter?.itemCount) margin else 0
        outRect.set(margin, margin/2, margin, bottom)
    }

}