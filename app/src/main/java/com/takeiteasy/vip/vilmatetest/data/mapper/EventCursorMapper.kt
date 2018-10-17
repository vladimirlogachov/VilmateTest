package com.takeiteasy.vip.vilmatetest.data.mapper

import android.database.Cursor
import com.takeiteasy.vip.vilmatetest.data.model.Event

class EventCursorMapper : CursorMapper<Event>() {
    override fun map(cursor: Cursor) = Event(
            cursor.getLong(Event.PROJECTION_ID_INDEX),
            cursor.getString(Event.PROJECTION_TITLE_INDEX),
            cursor.getString(Event.PROJECTION_ORGANIZER_INDEX),
            cursor.getLong(Event.PROJECTION_DTSTART_INDEX),
            cursor.getInt(Event.PROJECTION_HAS_ALARM_INDEX) == 1
    )
}