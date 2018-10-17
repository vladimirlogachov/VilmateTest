package com.takeiteasy.vip.vilmatetest.data.model

import android.net.Uri
import android.provider.CalendarContract.Events

class Event(
    val id: Long,
    val title: String,
    val organizer: String,
    val startDate: Long,
    val hasAlarm: Boolean
) {
    companion object {
        @JvmField val CONTENT_URI: Uri = Events.CONTENT_URI
        @JvmField val PROJECTION = arrayOf(
            Events._ID,
            Events.TITLE,
            Events.ORGANIZER,
            Events.DTSTART,
            Events.HAS_ALARM
        )

        const val PROJECTION_ID_INDEX = 0
        const val PROJECTION_TITLE_INDEX = 1
        const val PROJECTION_ORGANIZER_INDEX = 2
        const val PROJECTION_DTSTART_INDEX = 3
        const val PROJECTION_HAS_ALARM_INDEX = 4

        const val CALENDAR_SELECTION = Events.CALENDAR_ID + " = ?"
        const val DATE_RANGE_SELECTION = Events.DTSTART + " >= ? AND " + Events.DTSTART + " <= ?"

        const val SORTING_ORDER = Events.DTSTART + " ASC"
    }
}