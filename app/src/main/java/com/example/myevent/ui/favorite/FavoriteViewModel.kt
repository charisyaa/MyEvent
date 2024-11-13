package com.example.myevent.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myevent.database.EventEntity
import com.example.myevent.repository.EventRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean>get()  = _isFavorite

    private val mEventRepository: EventRepository = EventRepository(application)

    fun getAllFavoriteEvents(): LiveData<List<EventEntity>> {
        return mEventRepository.getAllFavoriteEvents()
    }

    // Memperbarui status favorit
    fun setFavoriteStatus(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

}