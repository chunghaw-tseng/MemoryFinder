package com.example.memoryfinder.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memoryfinder.R
import com.example.memoryfinder.data.PexelsApiService
import com.example.memoryfinder.databinding.MemoryViewerFragmentBinding
import com.example.memoryfinder.modelviews.MemoryViewerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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

        val apiService = PexelsApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val currentPexelResponse = apiService.getPhotos(keyword = "computer")
            binding.resultText.text = currentPexelResponse.toString()
        }
    }

}