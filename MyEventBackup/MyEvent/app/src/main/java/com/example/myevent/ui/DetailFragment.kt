package com.example.myevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.myevent.R
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.database.EventEntity
import com.example.myevent.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var registerButton: Button
    private lateinit var event: ListEventsItem


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        val detailViewModel by viewModels<DetailViewModel>{
            ViewModelFactory.getInstance(requireActivity().application)
        }

        // Mendapatkan ID event dari Bundle
        val eventId = arguments?.getString("EVENT_ID") ?: return
        val fragmentOrigin = arguments?.getString("FRAGMENT_ORIGIN")

        // Tentukan nilai isActive berdasarkan fragment
        val isActive = when (fragmentOrigin) {
            "HomeFragment" -> 1
            "DashboardFragment" -> 0
            else -> return
        }

        // Memanggil getEventDetail dengan eventId dan isActive
        detailViewModel.getEventDetail(eventId, isActive)

        // Mengamati perubahan data event detail
        detailViewModel.eventDetail.observe(viewLifecycleOwner) { detailEvent ->
            detailEvent?.let { // Memastikan detailEvent tidak null
                event = it // Simpan objek Event
                bindEventDetail(it)

                detailViewModel.checkIfEventFavorited(event.id.toString())
            }
        }

        // Mengamati status loading
        detailViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            showLoading(isLoading)
        }

        registerButton = view.findViewById(R.id.btnRegister)
        registerButton.setOnClickListener {
            openLinkInBrowser(event.link)
        }

        detailViewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFabIcon(isFavorite)
            Log.d("DetailFragment", "isFavorite: $isFavorite") // Tambahkan log ini untuk melihat perubahan isFavorite
        }

        binding.fabFavorite.setOnClickListener {
            val eventEntity = EventEntity(id = event.id.toString(), name = event.name, mediaCover = event.mediaCover)

            if (detailViewModel.isFavorite.value == true) {
                detailViewModel.delete(eventEntity) // Jika sudah favorite, hapus dari database
                Toast.makeText(requireContext(), "Event dihapus dari favorit!", Toast.LENGTH_SHORT).show()
            } else {
                detailViewModel.insert(eventEntity) // Jika belum favorite, tambahkan ke database
                Toast.makeText(requireContext(), "Event ditambahkan ke favorit!", Toast.LENGTH_SHORT).show()
            }
            detailViewModel.checkIfEventFavorited(event.id.toString())
        }
    }

    private fun openLinkInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }


    private fun bindEventDetail(detailEvent: ListEventsItem) {
        // Menampilkan data ke UI
        Glide.with(this)
            .load(detailEvent.mediaCover)
            .into(binding.imgEventCover)

        binding.tvEventName.text = detailEvent.name
        binding.tvOwnerName.text = getString(R.string.Owner, detailEvent.ownerName)
        binding.tvBeginTime.text = getString(R.string.Begin, detailEvent.beginTime)
        binding.tvQuota.text = getString(R.string.Quota_Remaining, detailEvent.quota - detailEvent.registrants)

        // Cek null sebelum menggunakan deskripsi
        val description = detailEvent.description
        binding.tvDescription.text = HtmlCompat.fromHtml(
            description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun updateFabIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24) // Ikon favorite penuh
        } else {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24) // Ikon favorite border
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}