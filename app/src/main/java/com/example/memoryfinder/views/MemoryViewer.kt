package com.example.memoryfinder.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memoryfinder.R
import com.example.memoryfinder.modelviews.MemoryViewerViewModel

class MemoryViewer : Fragment() {

    companion object {
        fun newInstance() = MemoryViewer()
    }

    private lateinit var viewModel: MemoryViewerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.memory_viewer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemoryViewerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}