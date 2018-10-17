package com.takeiteasy.vip.vilmatetest.presentation.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import com.takeiteasy.vip.vilmatetest.R
import com.takeiteasy.vip.vilmatetest.data.model.Event
import com.takeiteasy.vip.vilmatetest.data.provider.CalendarProvider
import com.takeiteasy.vip.vilmatetest.presentation.ui.CalendarView

class CalendarService : Service() {

    override fun onBind(p0: Intent?) = CalendarBinder(CalendarProvider(contentResolver))

    inner class CalendarBinder(private val calendarProvider: CalendarProvider) : Binder() {

        var calendarView: CalendarView? = null

        fun loadUpcomingEvents() {
            try {
                val calendar = calendarProvider.loadPrimaryCalendar()

                if (calendar != null) {
                    calendarView?.showEvents(calendarProvider.loadEvents(calendar.id))
                } else {
                    calendarView?.showError(getString(R.string.can_not_load_primary_calendar))
                }
            } catch (e: Exception) {
                calendarView?.showError(e.localizedMessage)
            }
        }

        fun setReminderFor(event: Event) {
            try {
                calendarProvider.setExactReminderFor(event)
            } catch (e: Exception) {
                calendarView?.showError(e.localizedMessage)
            }
        }

        fun removeReminderFor(event: Event) {
            try {
                calendarProvider.removeExactReminderFor(event)
            } catch (e: Exception) {
                calendarView?.showError(e.localizedMessage)
            }
        }

    }
}