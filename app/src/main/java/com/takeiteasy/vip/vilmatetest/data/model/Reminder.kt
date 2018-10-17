package com.takeiteasy.vip.vilmatetest.data.model

import android.net.Uri
import android.provider.CalendarContract.Reminders

class Reminder(
    val id: Long
) {
    companion object {
        @JvmField val CONTENT_URI: Uri = Reminders.CONTENT_URI
        @JvmField val PROJECTION = arrayOf(
            Reminders._ID
        )

        const val PROJECTION_ID_INDEX = 0

        const val EXACT_REMINDER_SELECTION = Reminders.EVENT_ID + " = ? AND " + Reminders.MINUTES + " = 0"
    }
}