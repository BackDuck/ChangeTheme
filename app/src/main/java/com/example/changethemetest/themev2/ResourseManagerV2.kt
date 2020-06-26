package com.example.changethemetest.themev2

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.changethemetest.R
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference

class ResourseManagerV2(val context: Context) {
    val defaultValue = context.resources.getString(R.string.default_them_prefix)
    val sharedPref = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    lateinit var themePrefix: String

    fun update(){
        themePrefix = sharedPref.getString(getString(R.string.theme_prefix_key), defaultValue)?:defaultValue
    }

    @Throws(IOException::class)
    fun getString(text: Int): String? {
        return context.getString(text)
    }

    fun getVectorDrawable(drawable: Int): Drawable? {
        return VectorDrawableCompat.create(context.resources, drawable, context.theme)
    }

//     fun getDrawable(name: String): Drawable? {
//        return ContextCompat.getDrawable(context, drawable)
//    }

    fun getTypefaceFromAsset(path: String): Typeface? {
        return Typeface.createFromAsset(context.assets, path)
    }

    fun getTypefaceFromFile(file: File): Typeface? {
        return Typeface.createFromFile(file)
    }

    fun getColor(color: String): Int {
        return ContextCompat.getColor(context,  context.resources.getIdentifier("${themePrefix}_$color", "color", context.packageName))
    }

    fun getDrawable(name: String): Drawable? {
        return if(themePrefix == defaultValue) {
            getStateDrawable(name)
        }else{
            null
        }
    }

    private fun getStateDrawable(name: String):Drawable{
        var weakButtonDrawableReference: WeakReference<Drawable>? = null

        var stateListDrawable = weakButtonDrawableReference?.get()
        if (stateListDrawable != null) {
            return stateListDrawable
        } else {
            val normal =  getDrawableIfExist("${themePrefix}_$name")
            val activate = getDrawableIfExist("${themePrefix}_${name}_active")
            val deactivate = getDrawableIfExist("${themePrefix}_${name}_deactive")

            stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_pressed, android.R.attr.state_enabled), normal)

            if (activate != null)
                stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), activate)
            if (deactivate != null)
                stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), deactivate)
            return stateListDrawable.also {
                weakButtonDrawableReference = WeakReference(it)
            }
        }
    }

    private fun getDrawableIfExist(id: String): Drawable?{
        return try {
            ContextCompat.getDrawable(context, context.resources.getIdentifier(id, "drawable", context.packageName))
        }catch (e: Exception){
            null
        }

    }
}