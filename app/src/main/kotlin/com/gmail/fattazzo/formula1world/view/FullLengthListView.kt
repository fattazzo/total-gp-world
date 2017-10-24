package com.gmail.fattazzo.formula1world.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

class FullLengthListView : ListView {

    var isExpanded = true

    constructor(context: Context, attrs: AttributeSet,
                defaultStyle: Int) : super(context, attrs, defaultStyle) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            val expandSpec = View.MeasureSpec.makeMeasureSpec(View.MEASURED_SIZE_MASK,
                    View.MeasureSpec.AT_MOST)
            super.onMeasure(widthMeasureSpec, expandSpec)

            val params = layoutParams
            params.height = measuredHeight
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}