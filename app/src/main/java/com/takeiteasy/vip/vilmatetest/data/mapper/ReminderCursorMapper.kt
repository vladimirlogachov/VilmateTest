package com.takeiteasy.vip.vilmatetest.data.mapper

import android.database.Cursor
import com.takeiteasy.vip.vilmatetest.data.model.Reminder

class ReminderCursorMapper : CursorMapper<Reminder>() {
    override fun map(cursor: Cursor)
            = Reminder(cursor.getLong(Reminder.PROJECTION_ID_INDEX))

}