package com.zee.amusicplayer.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun log(msg: String) {
    Log.d("zeeshan", "log: $msg")
}

fun log(msg: String = "", error: Exception? = null) {
    Log.d("zeeshan", "log: $msg $error")
    Log.e("zeeshan", "log: $msg", error)
}


fun showToast(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}