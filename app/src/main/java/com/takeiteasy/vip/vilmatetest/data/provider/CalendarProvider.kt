package com.takeiteasy.vip.vilmatetest.data.provider

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.provider.CalendarContract
import com.takeiteasy.vip.vilmatetest.data.mapper.CalendarCursorMapper
import com.takeiteasy.vip.vilmatetest.data.mapper.EventCursorMapper
import com.takeiteasy.vip.vilmatetest.data.mapper.ReminderCursorMapper
import com.takeiteasy.vip.vilmatetest.data.model.Calendar
import com.takeiteasy.vip.vilmatetest.data.model.Event
import com.takeiteasy.vip.vilmatetest.data.model.Reminder
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException
import java.util.concurrent.TimeUnit

class CalendarProvider(private val contentResolver: ContentResolver) {

    @Throws(SecurityException::class)
    fun loadPrimaryCalendar(): Calendar? {
        var calendar: Calendar? = null

        val cursor = contentResolver.query(
            Calendar.CONTENT_URI,
            Calendar.PROJECTION,
            Calendar.PRIMARY_CALENDAR_SELECTION,
            null,
            null
        )

        if (cursor != null) {
            cursor.moveToNext()
            calendar = CalendarCursorMapper().map(cursor)
            cursor.close()
        }

        return calendar
    }

    @Throws(SecurityException::class)
    fun loadEvents(calendarId: Long): List<Event> {
        val events = arrayListOf<Event>()
        val lowTimeBound = System.currentTimeMillis()
        val upTimeBound = lowTimeBound + TimeUnit.DAYS.toMillis(1)

        val cursor = contentResolver.query(
            Event.CONTENT_URI,
            Event.PROJECTION,
            Event.CALENDAR_SELECTION + " AND " + Event.DATE_RANGE_SELECTION,
            arrayOf(calendarId.toString(), lowTimeBound.toString(), upTimeBound.toString()),
            Event.SORTING_ORDER
        )

        if (cursor != null) {
            events.addAll(EventCursorMapper().mapList(cursor))
            cursor.close()
        }

        return events
    }

    @Throws(SecurityException::class)
    fun setExactReminderFor(event: Event) {
        val contentValues = ContentValues()
        contentValues.put(CalendarContract.Reminders.EVENT_ID, event.id)
        contentValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_DEFAULT)
        contentValues.put(CalendarContract.Reminders.MINUTES, 0)

        contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, contentValues)
    }

    @Throws(SecurityException::class, Exception::class)
    fun removeExactReminderFor(event: Event) {
        val reminder = getExactReminderFor(event)

        if (reminder != null) {
            val deleteUri = ContentUris.withAppendedId(Reminder.CONTENT_URI, reminder.id)
            contentResolver.delete(deleteUri, null, null)
        } else {
            throw NoSuchElementException()
        }
    }

    @Throws(SecurityException::class)
    private fun getExactReminderFor(event: Event): Reminder? {
        var reminder: Reminder? = null

        val cursor = contentResolver.query(
            Reminder.CONTENT_URI,
            Reminder.PROJECTION,
            Reminder.EXACT_REMINDER_SELECTION,
            arrayOf(event.id.toString()),
            null
        )

        if (cursor != null) {
            cursor.moveToNext()
            reminder = ReminderCursorMapper().map(cursor)
            cursor.close()
        }

        return reminder
    }
}