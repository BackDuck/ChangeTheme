package com.example.changethemetest.themev2

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.changethemetest.R
import java.lang.StringBuilder


class ThemeController {
    var rootItem = true
    lateinit var context: Context
    val resourseManager: ResourseManagerV2 by lazy { ResourseManagerV2(context) }

    fun apply(root: ViewGroup) {
        context = root.context
        resourseManager.update()


        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 200
            addUpdateListener {
                val alpha = it.animatedValue as Float
                root.alpha = alpha
            }

            addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    applyTheme(root)
                    updateTheme(root)
                    ValueAnimator.ofFloat(0f, 1f).apply {
                        duration = 300
                        addUpdateListener {
                            val alpha = it.animatedValue as Float
                            root.alpha = alpha
                        }
                        start()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}

            })

            start()
        }
    }

    private fun updateTheme(root: ViewGroup){

        for (pos in 0 until root.childCount) {
            val v: View = root.getChildAt(pos)
            val vg: ViewGroup? = v as? ViewGroup
            if (vg != null) {
                updateTheme(vg)
            }
            if (v.tag != null && !(v.tag as? String).isNullOrBlank()) {
                applyTheme(v)
            }
        }
    }


    private fun applyTheme(view: View) {
        (view.tag as? String)?.let { tag ->

            when (tag) {
                /* "ImageButton" -> {
                 (view as ImageButton).setImageDrawable(resourseManager.getDrawable(tag))
             }

             "Button" -> {
                 (view as Button).background = resourseManager.getDrawable(tag)
             }*/

                getString(R.string.standard_text_view) -> {
                    val id = getString(R.string.standard_text_view)
                    val targetView = view as TextView

                    val textColor = resourseManager.getColor(textColorId(id))
                    val bgColor = resourseManager.getColor(bgColorId(id))

                    targetView.setTextColor(textColor)
                    targetView.setBackgroundColor(bgColor)

                }

                getString(R.string.light_box) -> {
                    val id = getString(R.string.light_box)
                    val targetView = view as ViewGroup

                    val bgColor = resourseManager.getColor(bgColorId(id))

                    targetView.setBackgroundColor(bgColor)
                }

                getString(R.string.toolbar) -> {
                    val id = getString(R.string.toolbar)
                    val targetView = view as Toolbar

                    val bgColor = resourseManager.getColor(bgColorId(id))
                    val textColor = resourseManager.getColor(textColorId(id))

                    targetView.setBackgroundColor(bgColor)
                    targetView.setTitleTextColor(textColor)
                }

                getString(R.string.switcher) -> {
                    val id = getString(R.string.switcher)
                    val targetView = view as Switch

                    val textColor = resourseManager.getColor(textColorId(id))

                    targetView.setTextColor(textColor)
                }
            }
        }
    }

    private fun getString(id: Int) = context.getString(id)

    private fun bgColorId(id: String) = StringBuilder().append(id).append("_bg").toString()
    private fun textColorId(id: String) =
        StringBuilder().append(id).append("_text_color").toString()


}