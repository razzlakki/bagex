package com.tezsol.bagex.custom.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class BagexTextView : androidx.appcompat.widget.AppCompatTextView {
    
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTypeFace()
    }

    private fun initTypeFace() {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/poppins_medium.otf")
    }

}