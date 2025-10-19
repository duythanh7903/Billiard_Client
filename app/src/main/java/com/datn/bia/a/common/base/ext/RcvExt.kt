package com.datn.bia.a.common.base.ext

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addItemDecoration(
    marginStart: Int,
    marginTop: Int,
    marginEnd: Int,
    marginBottom: Int
) {
    this.addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) >= 0) {
                outRect.left = marginStart
                outRect.top = marginTop
                outRect.right = marginEnd
                outRect.bottom = marginBottom
            }
        }
    })
}