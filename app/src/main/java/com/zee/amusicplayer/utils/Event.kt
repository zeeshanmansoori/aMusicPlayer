package com.zee.amusicplayer.utils

open class Event<out T>(private val data: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            return data
        }

    }

    fun peekContent() = data
}