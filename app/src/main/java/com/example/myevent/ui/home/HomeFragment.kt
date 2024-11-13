package com.example.myevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myevent.R
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.databinding.FragmentHomeBinding
import com.example.myevent.ui.EventAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var eventAdapter: EventAdapter

    companion object{
        private const val TAG = "HomeFragment"
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearGridLayoutManager = LinearLayoutManager(context)
        binding.rvHome.layoutManager = linearGridLayoutManager

        eventAdapter = EventAdapter{ event -> onEventClick(event) }
        binding.rvHome.adapter = eventAdapter

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.eventUpcoming.observe(viewLifecycleOwner) { upcoming ->
            setEventData(upcoming)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun onEventClick(event: ListEventsItem) {
        // Membuat Bundle untuk mengirimkan data ke DetailFragment
        val bundle = Bundle().apply {
            putString("EVENT_ID", event.id.toString())
            putString("FRAGMENT_ORIGIN", "HomeFragment")
        }
        findNavController().navigate(R.id.action_navigation_home_to_navigation_detail_home, bundle)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setEventData(eventList: List<ListEventsItem>){
        eventAdapter.submitList(eventList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}