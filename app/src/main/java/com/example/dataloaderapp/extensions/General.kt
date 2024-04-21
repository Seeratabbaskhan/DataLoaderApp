package com.example.dataloaderapp.extensions

import android.content.Context
import android.view.View
import android.view.WindowManager.BadTokenException
import android.widget.Toast

private var toast: Toast? = null
fun Context.showToast(message: String) {

    toast?.cancel()
    try {
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    } catch (_: Exception) {
    } catch (_: BadTokenException) {
    }

}

fun View.isRTL(): Boolean {
    return this.layoutDirection == View.LAYOUT_DIRECTION_RTL
}



fun Long.formatTime(): String {
    val days = this / (24 * 3600)
    val hours = (this % (24 * 3600)) / 3600
    val minutes = ((this % (24 * 3600)) % 3600) / 60

    return if (days > 0) {
        String.format("%dd : %02dh : %02dm", days, hours, minutes)
    } else {
        String.format("0d : %02dh : %02dm", hours, minutes)
    }
}



