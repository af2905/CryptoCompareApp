package ru.job4j.cryptocompareapp.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DivItemDecoration(private val horizontalPadding: Int, private val verticalPadding: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top += verticalPadding
        outRect.bottom += verticalPadding
        outRect.left += horizontalPadding
        outRect.right += horizontalPadding
    }
}
