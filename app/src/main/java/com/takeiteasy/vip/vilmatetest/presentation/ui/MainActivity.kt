package com.takeiteasy.vip.vilmatetest.presentation.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.takeiteasy.vip.vilmatetest.R
import com.takeiteasy.vip.vilmatetest.data.model.Event
import com.takeiteasy.vip.vilmatetest.presentation.service.CalendarService
import com.takeiteasy.vip.vilmatetest.presentation.ui.adapter.EventsAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity
    : AppCompatActivity(), CalendarView, EventsAdapter.EventInteractionsListener {

    companion object {
        const val PERMISSIONS_REQUEST = 0x1010
    }

    lateinit var calendarBinder: CalendarService.CalendarBinder

    private var isBounded = false
    private val adapter = EventsAdapter(this)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            calendarBinder = iBinder as CalendarService.CalendarBinder
            calendarBinder.calendarView = this@MainActivity
            calendarBinder.loadUpcomingEvents()
            isBounded = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            calendarBinder.calendarView = null
            isBounded = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        events.layoutManager = LinearLayoutManager(this)
        events.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        events.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
        || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR), PERMISSIONS_REQUEST)
        } else {
            bindCalendarService()
        }
    }

    private fun bindCalendarService() {
        val intent = Intent(this, CalendarService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBounded) {
            unbindService(serviceConnection)
            isBounded = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    bindCalendarService()
                } else {
                    Toast.makeText(this, R.string.mandatory_permissions_error, Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showEvents(events: List<Event>) {
        adapter.updateData(events)
    }

    override fun setReminderFor(event: Event) {
        calendarBinder.setReminderFor(event)
    }

    override fun removeReminderFor(event: Event) {
        calendarBinder.removeReminderFor(event)
    }
}
