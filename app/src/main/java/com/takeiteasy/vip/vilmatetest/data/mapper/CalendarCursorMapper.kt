package com.takeiteasy.vip.vilmatetest.data.mapper

import android.database.Cursor
import com.takeiteasy.vip.vilmatetest.data.model.Calendar

class CalendarCursorMapper : CursorMapper<Calendar>() {
    override fun map(cursor: Cursor) = Calendar(
            cursor.getLong(Calendar.PROJECTION_ID_INDEX),
            cursor.getString(Calendar.PROJECTION_ACCOUNT_NAME_INDEX),
            cursor.getString(Calendar.PROJECTION_DISPLAY_NAME_INDEX),
            cursor.getString(Calendar.PROJECTION_OWNER_ACCOUNT_INDEX)
    )
}