package com.takeiteasy.vip.vilmatetest.presentation.ui.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.takeiteasy.vip.vilmatetest.R
import com.takeiteasy.vip.vilmatetest.data.model.Event
import com.takeiteasy.vip.vilmatetest.presentation.util.DateUtil
import kotlinx.android.synthetic.main.item_event.view.*
import java.util.*

class EventsAdapter(val listener: EventInteractionsListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_EMPTY_STATE = 0
        const val ITEM_TYPE_EVENT = 1
    }

    private val data = mutableListOf<Event>()

    fun updateData(data: List<Event> ) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_EMPTY_STATE) {
            EmptyStateViewHolder(inflateView(parent, R.layout.item_events_empty_state))
        } else {
            EventViewHolder(inflateView(parent, R.layout.item_event))
        }
    }

    private fun inflateView(parent: ViewGroup, @LayoutRes layoutRes: Int)
        = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

    override fun getItemCount(): Int {
        return if (data.isEmpty()) {
            1
        } else {
            data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isEmpty()) {
            ITEM_TYPE_EMPTY_STATE
        } else {
            ITEM_TYPE_EVENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ListItemBinder<Event>)?.bind(data[position])
    }

    inner class EmptyStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ListItemBinder<Event> {

        override fun bind(item: Event) = with(itemView)  {
            start_date.text = DateUtil.formatDate(Date(item.startDate), DateUtil.DATE_TIME_FORMAT)
            title.text = item.title
            organizer.text = item.organizer
            reminder_toggle.setOnCheckedChangeListener(null)
            reminder_toggle.isChecked = item.hasAlarm
            reminder_toggle.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    listener.setReminderFor(item)
                } else {
                    listener.removeReminderFor(item)
                }
            }
        }
    }

    interface EventInteractionsListener {
        fun setReminderFor(event: Event)
        fun removeReminderFor(event: Event)
    }
}