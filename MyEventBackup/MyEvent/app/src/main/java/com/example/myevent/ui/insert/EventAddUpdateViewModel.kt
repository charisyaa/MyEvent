package com.example.myevent.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.myevent.database.EventEntity
import com.example.myevent.repository.EventRepository

class EventAddUpdateViewModel(application: Application) : ViewModel() {
    private val mEventRepository: EventRepository = EventRepository(application)

    fun insert(note: EventEntity) {
        mEventRepository.insert(note)
    }

    fun update(note: EventEntity) {
        mEventRepository.update(note)
    }

    fun delete(note: EventEntity) {
        mEventRepository.delete(note)
    }
}