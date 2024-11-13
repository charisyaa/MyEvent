    package com.example.myevent.ui

    import android.app.Application
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.myevent.data.response.EventResponse
    import com.example.myevent.data.response.ListEventsItem
    import com.example.myevent.data.retrofit.ApiConfig
    import com.example.myevent.database.EventEntity
    import com.example.myevent.repository.EventRepository
    import kotlinx.coroutines.launch
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class DetailViewModel(application: Application) : ViewModel() {
        private val mEventRepository: EventRepository = EventRepository(application)

        fun getEventEntityById(id: String): LiveData<List<EventEntity>> = mEventRepository.getEventEntityById(id)

        private val _eventDetail = MutableLiveData<ListEventsItem?>()
        val eventDetail: LiveData<ListEventsItem?> = _eventDetail

        private val _isLoading = MutableLiveData<Boolean>()
        val isLoading : LiveData<Boolean> = _isLoading

        private val _isFavorite = MutableLiveData<Boolean>()
        val isFavorite: LiveData<Boolean> = _isFavorite

        companion object {
            private const val TAG = "DetailViewModel"
        }


        fun getEventDetail(eventId: String, isActive: Int) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getActiveEvents(isActive)
            client.enqueue(object : Callback<EventResponse> {
                override fun onResponse(
                    call: Call<EventResponse>,
                    response: Response<EventResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val event = response.body()?.listEvents?.find { it.id.toString() == eventId }
                        _eventDetail.value = event // Set detail event
                    } else {
                        // Handle error case
                    }
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    _isLoading.value = false
                    // Handle failure case
                }
            })
        }

        fun checkIfEventFavorited(eventId: String) {
            getEventEntityById(eventId).observeForever { eventEntities ->
                // Jika eventEntities kosong, berarti event belum difavoritkan
                _isFavorite.value = eventEntities.isNotEmpty()
            }
        }

        fun insert(eventEntity: EventEntity) {
            viewModelScope.launch {
                mEventRepository.insert(eventEntity)
            }
        }

        fun delete(eventEntity: EventEntity) {
            viewModelScope.launch {
                mEventRepository.delete(eventEntity)
            }
        }

    }