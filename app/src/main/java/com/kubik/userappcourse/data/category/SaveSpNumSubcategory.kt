package com.kubik.userappcourse.data.category

import android.content.Context

internal class SaveSpNumSubcategory(private val context: Context) {

    private val NUM_SUBCATEGORY = "NUM_SUBCATEGORY"

    fun saveNumSubcategory(numCategory: Int) {
        val editor = getSp().edit()
        editor.clear()
        editor.putInt(NUM_SUBCATEGORY, numCategory)
        editor.apply()
    }

    fun getNumSubcategory(): Int = getSp().getInt(NUM_SUBCATEGORY, 0)

    private fun getSp() = context.getSharedPreferences("SAVE_NUM_SUBCATEGORY", Context.MODE_PRIVATE)
}