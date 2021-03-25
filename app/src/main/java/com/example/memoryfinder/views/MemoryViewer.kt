package com.example.memoryfinder.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.memoryfinder.R
import com.example.memoryfinder.data.network.ConnectivityImpl
import com.example.memoryfinder.data.network.PexelNetworkDS
import com.example.memoryfinder.data.network.PexelNetworkDSImpl
import com.example.memoryfinder.data.network.PexelsApiService
import com.example.memoryfinder.databinding.MemoryViewerFragmentBinding
import com.example.memoryfinder.modelviews.MemoryViewerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MemoryViewer : Fragment(R.layout.memory_viewer_fragment) {

    companion object {
        fun newInstance() = MemoryViewer()
    }

    private lateinit var viewModel: MemoryViewerViewModel

    private var _binding: MemoryViewerFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MemoryViewerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemoryViewerViewModel::class.java)
        // TODO: Use the ViewModel

        val apiService = PexelsApiService(ConnectivityImpl(this.requireContext()))
        val dataSource = PexelNetworkDSImpl(apiService)

        dataSource.downloadedCurrentMemories.observe(viewLifecycleOwner, Observer {
            binding.resultText.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
           dataSource.fetchImages("computer")

        }
    }

}