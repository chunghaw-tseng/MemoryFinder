package com.example.memoryfinder.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.memoryfinder.R
import com.example.memoryfinder.databinding.MemoryViewerFragmentBinding
import com.example.memoryfinder.modelviews.MemoryViewerModelFactory
import com.example.memoryfinder.modelviews.MemoryViewerViewModel
import kotlinx.coroutines.*
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

class MemoryViewer : Fragment(R.layout.memory_viewer_fragment), DIAware, CoroutineScope {

    override val di by closestDI()
    private val memoryViewerModelFactory : MemoryViewerModelFactory by instance()
    private var job = Job()

    private lateinit var viewModel: MemoryViewerViewModel
    private val TAG:String = "MainFragment"
    private var _binding: MemoryViewerFragmentBinding? = null
    private val binding get() = _binding!!

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MemoryViewerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, memoryViewerModelFactory)
            .get(MemoryViewerViewModel::class.java)

        bindUI()


//        GlobalScope.launch(Dispatchers.Main) {
//           dataSource.fetchImages("computer", page="1")
//        }
    }

    private fun bindUI() = launch{
        val foundImages = viewModel.images.await()
        foundImages.observe(viewLifecycleOwner, Observer {
            binding.textView.text = it.toString()
        })
    }




}