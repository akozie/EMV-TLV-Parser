package com.isw.emvtlvparser.utils

import android.graphics.Color

object Utils {

    fun getTagColor(tag: String): Int {
        return when (tag.uppercase()) {
            "9F02", "5A", "57", "9F26", "9F10", "84" -> Color.parseColor("#FF5722")
            else -> Color.BLACK
        }
    }

}