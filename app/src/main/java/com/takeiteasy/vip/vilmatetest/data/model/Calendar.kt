package com.takeiteasy.vip.vilmatetest.data.model

import android.net.Uri
import android.provider.CalendarContract.Calendars

class Calendar(
    val id: Long,
    val displayName: String,
    val accountName: String,
    val ownerName: String
) {
    companion object {
        @JvmField val CONTENT_URI: Uri = Calendars.CONTENT_URI
        @JvmField val PROJECTION = arrayOf(
            Calendars._ID,
            Calendars.ACCOUNT_NAME,
            Calendars.CALENDAR_DISPLAY_NAME,
            Calendars.OWNER_ACCOUNT
        )

        const val PROJECTION_ID_INDEX = 0
        const val PROJECTION_ACCOUNT_NAME_INDEX = 1
        const val PROJECTION_DISPLAY_NAME_INDEX = 2
        const val PROJECTION_OWNER_ACCOUNT_INDEX = 3

        const val PRIMARY_CALENDAR_SELECTION = Calendars.IS_PRIMARY + " = 1"
    }
}