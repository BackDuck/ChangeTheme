package com.example.changethemetest

import android.R
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.changethemetest.themev2.ThemeController

abstract class BaseActivity : AppCompatActivity() {

    private val tc = ThemeController()

    val root by lazy { (findViewById<View>(R.id.content) as ViewGroup).getChildAt(0) as ViewGroup }

    override fun onResume() {
        super.onResume()
        applyTheme()
    }

    fun applyTheme() {
        tc.apply(root)
    }
}