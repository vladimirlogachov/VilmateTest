package com.takeiteasy.vip.vilmatetest.presentation.ui

import com.takeiteasy.vip.vilmatetest.data.model.Event

interface CalendarView {
    fun showError(error: String)
    fun showEvents(events: List<Event>)
}