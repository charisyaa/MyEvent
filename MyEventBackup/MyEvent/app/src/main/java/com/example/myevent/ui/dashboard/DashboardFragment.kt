package com.example.myevent.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myevent.R
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.databinding.FragmentDashboardBinding
import com.example.myevent.ui.EventAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var eventAdapter: EventAdapter

    companion object{
        private const val TAG = "DashboardFragment"
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvDashboard.layoutManager = staggeredGridLayoutManager

        eventAdapter = EventAdapter{ event -> onEventClick(event) }
        binding.rvDashboard.adapter = eventAdapter

        val finishedViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        finishedViewModel.eventFinished.observe(viewLifecycleOwner) { finishedEvents ->
            setEventData(finishedEvents)
        }
        finishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun onEventClick(event: ListEventsItem) {
        // Membuat Bundle untuk mengirimkan data ke DetailFragment
        val bundle = Bundle().apply {
            putString("EVENT_ID", event.id.toString())
            putString("FRAGMENT_ORIGIN", "DashboardFragment")
        }
        findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_detail_home, bundle)
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