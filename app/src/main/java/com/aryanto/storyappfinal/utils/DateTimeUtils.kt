package com.aryanto.storyappfinal.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatCreatedAd(createdAt: String): String {
        val createAtInstant = Instant.parse(createdAt)
        val createAtLocalDT = LocalDateTime.ofInstant(createAtInstant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val now = LocalDateTime.now(ZoneId.systemDefault())
        val duration = Duration.between(createAtLocalDT, now)

        return when {
            duration.toMinutes() < 1 -> "Just posted now"
            duration.toMinutes() < 60 -> "Posted ${duration.toMinutes()} ${if (duration.toMinutes() == 1L) "minute" else "minutes"} ago"
            duration.toHours() < 24 -> "Posted ${duration.toHours()} ${if (duration.toHours() == 1L) "hour" else "hours"} ago"
            else -> "Posted ${formatter.format(createAtLocalDT)} ago"
        }
    }

}