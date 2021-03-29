package com.example.memoryfinder.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryfinder.R
import com.example.memoryfinder.adapters.RecyclerAdapter
import com.example.memoryfinder.data.provider.SearchProvider
import com.example.memoryfinder.data.provider.SearchProviderImpl
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
    private lateinit var mainrecycler: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private val TAG:String = "MainFragment"
    private var _binding: MemoryViewerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var scrollListener : RecyclerView.OnScrollListener

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
        mainrecycler = binding.memoryView
        gridLayoutManager = GridLayoutManager(this.context, 3)
        setRecyclerViewScrollListener()
        mainrecycler.layoutManager = gridLayoutManager
        adapter = RecyclerAdapter(requireContext())
        mainrecycler.adapter = adapter
        bindUI()
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemcount = gridLayoutManager.itemCount
                val lastitem = gridLayoutManager.findLastVisibleItemPosition()
                Log.d(TAG, totalItemcount.toString())
                Log.d(TAG, lastitem.toString())
                // Figure out how to load more
                if (totalItemcount <= (lastitem + 20)){
                    Log.d(TAG, "Load More ")
                    // TODO  It's loading far more than it should

                    // Call only if it needs loading

                    viewModel.nextPage()
                }

            }
        }
        mainrecycler.addOnScrollListener(scrollListener)
    }

    // Will have to update the UI from this call
    private fun bindUI() = launch{
        val foundImages = viewModel.images.await()
        foundImages.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
//            binding.textView.text = it.toString()
            Log.d(TAG, "Gotten ${it.size} images")
            adapter.addPhotos(it)
            adapter.notifyDataSetChanged()
        })
    }




}