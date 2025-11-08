package com.sujoy.mindmate.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UtilityMethods {
    companion object {

        fun formatDate(milliseconds: Long): String {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }
    }
}