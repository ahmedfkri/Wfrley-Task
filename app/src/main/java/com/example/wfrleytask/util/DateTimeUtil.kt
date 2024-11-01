package com.example.wfrleytask.util

import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtil {

    fun formatDateToArabic(dateString: String): String {
        try {

            val parsedDate = LocalDateTime.parse(
                dateString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS")
            )

            val formatter = DateTimeFormatter.ofPattern(" d MMMM yyyy , h:mm", Locale("ar"))
            return parsedDate.format(formatter)
        } catch (Exception: Exception) {
            Log.d("TAG", "invoke: $Exception")
            return "في الماضي"
        }

    }

    fun getTodayDate(): String {
        val today = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ar"))
        return today.format(formatter)
    }

}