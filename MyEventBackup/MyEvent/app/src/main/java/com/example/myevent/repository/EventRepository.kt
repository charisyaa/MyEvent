package com.example.myevent.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myevent.database.EventDao
import com.example.myevent.database.EventEntity
import com.example.myevent.database.EventRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventRepository(application: Application) {
    private val mEventDao: EventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = EventRoomDatabase.getDatabase(application)
        mEventDao = db.eventDao()
    }

    fun getEventEntityById(id: String): LiveData<List<EventEntity>> = mEventDao.getEventEntityById(id)

    fun insert(note: EventEntity) {
        executorService.execute { mEventDao.insert(note) }
    }

    fun delete(note: EventEntity) {
        executorService.execute { mEventDao.delete(note) }
    }

    fun update(note: EventEntity) {
        executorService.execute { mEventDao.update(note) }
    }
}