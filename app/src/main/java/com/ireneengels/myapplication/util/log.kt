package com.ireneengels.myapplication.util

import android.util.Log

fun log(message: String, tag: String = "MY_TAG") {
    Log.d(tag, message)
}

fun logError(message: String, tag: String = "MY_TAG") {
    Log.e(tag, message)
}