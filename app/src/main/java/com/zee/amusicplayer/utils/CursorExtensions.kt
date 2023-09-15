/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.zee.amusicplayer.utils

import android.annotation.SuppressLint
import android.database.Cursor
import org.json.JSONObject

// exception is rethrown manually in order to have a readable stacktrace

@SuppressLint("Range")
internal fun Cursor.getInt(columnName: String): Int {
    try {
        return this.getInt(this.getColumnIndex(columnName))
    } catch (ex: Throwable) {
        throw IllegalStateException("invalid column $columnName", ex)
    }
}

@SuppressLint("Range")
internal fun Cursor.getLong(columnName: String, default: Long = -1): Long {
    return try {
        this.getLong(this.getColumnIndex(columnName))
    } catch (ex: Exception) {
        ex.printStackTrace()
        default
    }
}

@SuppressLint("Range")
internal fun Cursor.getStringOrNull(columnName: String): String? {
    return try {
        this.getString(this.getColumnIndex(columnName))
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}


internal fun JSONObject.getStringSafely(name: String): String {
    return try {
        getString(name)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}