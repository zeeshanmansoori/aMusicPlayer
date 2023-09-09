package com.zee.amusicplayer.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue

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



@OptIn(ExperimentalMaterial3Api::class)
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        //todo
        val fraction =1f
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue
        return when {
            currentValue == SheetValue.Hidden && targetValue == SheetValue.Hidden -> 0f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 1f
            currentValue == SheetValue.Hidden && targetValue == SheetValue.Expanded -> fraction
            else -> 1f - fraction
        }

    }