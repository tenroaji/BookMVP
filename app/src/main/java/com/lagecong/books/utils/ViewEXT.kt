package com.lagecong.books.utils

import android.view.View
import android.widget.CheckBox

infix fun View.visibility(status: Boolean) {
    visibility =
        if (status)
            View.VISIBLE
        else
            View.GONE
}
