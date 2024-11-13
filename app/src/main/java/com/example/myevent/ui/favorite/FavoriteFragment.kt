package com.example.myevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myevent.R
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.databinding.FragmentFavoriteBinding
import com.example.myevent.ui.EventAdapter
import com.example.myevent.ui.ViewModelFactory

class FavoriteFragment: Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by viewModels { ViewModelFactory.getInstance(requireActivity().application) }
    private lateinit var adapter: EventAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearGridLayoutManager = LinearLayoutManager(context)
        binding.rvFavorite.layoutManager = linearGridLayoutManager

        adapter = EventAdapter { event -> onEventClick(event) }
        binding.rvFavorite.adapter = adapter

        // Mengamati perubahan data favorit
        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { eventEntities ->
            val events = eventEntities.map { eventEntity ->
                // Konversi EventEntity menjadi ListEventsItem
                ListEventsItem(
                    summary = "",
                    mediaCover = "",
                    registrants = 0,
                    imageLogo = eventEntity.mediaCover ?: "",
                    link = "",
                    ownerName = "",
                    description = "",
                    cityName = "",
                    quota = 0,
                    name = eventEntity.name,
                    id = eventEntity.id.toInt(),
                    beginTime = "",
                    endTime = "",
                    category = "",
                )
            }
            adapter.submitList(events)
        }
    }

    private fun onEventClick(event: ListEventsItem) {
        favoriteViewModel.setFavoriteStatus(true)
        val bundle = Bundle().apply {
            putString("EVENT_ID", event.id.toString())
            putString("FRAGMENT_ORIGIN", "FavoriteFragment")
        }
        findNavController().navigate(R.id.action_navigation_favorite_to_navigation_detail_home, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}