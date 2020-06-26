package com.example.changethemetest.themev2

import android.view.ViewGroup
import android.view.ViewTreeObserver

class ThemeHelper {

    val themeController = ThemeController()

    fun watch(view: ViewGroup) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                themeController.apply(view)
            }
        })
    }
}


