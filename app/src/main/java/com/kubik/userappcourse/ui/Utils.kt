package com.kubik.userappcourse.ui

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.kubik.userappcourse.R

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLongToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun checkNotEmptyEditText(editText: EditText): Boolean =
    !(editText.text.isEmpty() || editText.text.toString() == "")

fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
}

fun getStatusProduct(numStatus: Int, context: Context) = when (numStatus) {
    0, 3 -> context.getString(R.string.on_assembly)
    1 -> context.getString(R.string.ready_for_pickup)
    else -> context.getString(R.string.item_received)
}
