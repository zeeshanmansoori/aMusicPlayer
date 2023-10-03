package com.zee.amusicplayer.utils

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}