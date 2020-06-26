package com.example.changethemetest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    val PREF = "theme"
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Change Theme";

        sp = getSharedPreferences(PREF, Context.MODE_PRIVATE)

        switch1.setOnClickListener {
            writeInPref()
            applyTheme()
        }
    }

    fun writeInPref() {

        val key = getString(R.string.theme_prefix_key)
        val defaultTheme = getString(R.string.default_them_prefix)
        val darkTheme = getString(R.string.dark_them_prefix)

        val e: SharedPreferences.Editor = sp.edit()
        if(sp.getString(key,  defaultTheme)?:defaultTheme == defaultTheme){
            e.putString(key, darkTheme)
        }else{
            e.putString(key, defaultTheme)
        }

        e.commit()

    }


}