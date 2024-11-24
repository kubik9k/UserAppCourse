package com.kubik.userappcourse.data.category

import android.content.Context

internal class SaveSpNumCategory(private val context: Context) {

    private val NUM_CATEGORY = "NUM_CATEGORY"

    fun saveNumCategory(numCategory: Int) {
        val editor = getSp().edit()
        editor.clear()
        editor.putInt(NUM_CATEGORY, numCategory)
        editor.apply()
    }

    fun getNumCategory(): Int = getSp().getInt(NUM_CATEGORY, 0)

    private fun getSp() = context.getSharedPreferences("SAVE_NUM_CATEGORY", Context.MODE_PRIVATE)
}