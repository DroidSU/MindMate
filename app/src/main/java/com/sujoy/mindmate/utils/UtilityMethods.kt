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

        fun formatMillisToWeeks(milliseconds: Long): String {
            val formatter = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }

        fun formatMillisToTime(milliseconds: Long): String {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return formatter.format(Date(milliseconds))
        }
    }
}