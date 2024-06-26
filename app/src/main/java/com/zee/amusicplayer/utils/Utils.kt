package com.zee.amusicplayer.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi

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


@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        val fraction = bottomSheetState.progress
        // TODO(need to update targetValue)
        val targetValue = bottomSheetState.currentValue
        val currentValue = bottomSheetState.currentValue
        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 0f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 1f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> fraction
            else -> 1f - fraction
        }

    }