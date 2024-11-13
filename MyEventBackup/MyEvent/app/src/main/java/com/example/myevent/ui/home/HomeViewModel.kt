package com.example.myevent.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myevent.data.response.EventResponse
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.data.retrofit.ApiConfig
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _eventUpcoming = MutableLiveData<List<ListEventsItem>>()
    val eventUpcoming : LiveData<List<ListEventsItem>> = _eventUpcoming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "HomeViewModel"
    }

    init {
        findEvent()
    }

    private fun findEvent() {
        _isLoading.value = true
        //Request API
        val client = ApiConfig.getApiService().getActiveEvents(1)
        client.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: retrofit2.Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _eventUpcoming.value = response.body()?.listEvents
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}