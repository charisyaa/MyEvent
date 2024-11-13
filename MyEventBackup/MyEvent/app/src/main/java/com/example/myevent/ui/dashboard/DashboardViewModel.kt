package com.example.myevent.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myevent.data.response.EventResponse
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.data.retrofit.ApiConfig
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val _eventFinished = MutableLiveData<List<ListEventsItem>>()
    val eventFinished : LiveData<List<ListEventsItem>> = _eventFinished

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DashboardViewModel"
    }

    init {
        findEvent()
    }

    private fun findEvent() {
        _isLoading.value = true
        //Request API
        val client = ApiConfig.getApiService().getActiveEvents(0)
        client.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: retrofit2.Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _eventFinished.value = response.body()?.listEvents
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