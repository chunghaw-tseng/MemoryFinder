package com.example.memoryfinder.views

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memoryfinder.R
import com.example.memoryfinder.adapters.OnItemClickListener
import com.example.memoryfinder.adapters.PexelLoadStateAdapter
import com.example.memoryfinder.adapters.PexelPhotoAdapter
import com.example.memoryfinder.adapters.RecyclerAdapter
import com.example.memoryfinder.data.model.Photo
import com.example.memoryfinder.databinding.MemoryViewerFragmentBinding
import com.example.memoryfinder.modelviews.MemoryViewModel
import com.example.memoryfinder.modelviews.MemoryViewerModelFactory
import com.example.memoryfinder.modelviews.MemoryViewerViewModel
import kotlinx.coroutines.*
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance
import kotlin.coroutines.CoroutineContext

class MemoryViewer : Fragment(R.layout.memory_viewer_fragment), DIAware, CoroutineScope, OnItemClickListener {

    override val di by closestDI()
    private val memoryViewerModelFactory : MemoryViewerModelFactory by instance()
    private var job = Job()

    private lateinit var viewModel: MemoryViewModel
    private lateinit var mainrecycler: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.pexel_menu, menu)
        val searchItem = menu.findItem(R.id.searchAction)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    mainrecycler.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }
        })
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

    override fun onitemClick(photo: Photo) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.image_dialog)
        val progress = dialog.findViewById(R.id.progress_circular) as ProgressBar
        val body = dialog.findViewById(R.id.fullImage) as ImageView
        Glide.with(this).load(photo.src.original).error(R.drawable.load_error)
            .listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                TODO("Not yet implemented")
            }
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Log.d(TAG, "Resource Ready")
                progress.visibility = View.GONE
                body.visibility = View.VISIBLE
                return false
            }
        }).into(body)
        dialog.show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
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
            .get(MemoryViewModel::class.java)
        mainrecycler = binding.memoryView

        gridLayoutManager = GridLayoutManager(this.context, 2)
//        setRecyclerViewScrollListener()
        val adapter = PexelPhotoAdapter(this)

        binding.apply {
            memoryView.setHasFixedSize(true)
            memoryView.itemAnimator = null
            memoryView.layoutManager = gridLayoutManager
            // TODO Deal with this
            memoryView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PexelLoadStateAdapter {
                    adapter.retry()
                },
                footer = PexelLoadStateAdapter{
                    adapter.retry()
                }
            )
            btnRetry.setOnClickListener{
                adapter.retry()
            }
        }

        adapter.addLoadStateListener {  loadstate ->
            binding.apply {
                progressBar.isVisible = loadstate.source.refresh is LoadState.Loading
                memoryView.isVisible = loadstate.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadstate.source.refresh is LoadState.Error
                textError.isVisible = loadstate.source.refresh is LoadState.Error

                // For the empty view
                if (loadstate.source.refresh is LoadState.NotLoading &&
                        loadstate.append.endOfPaginationReached &&
                        adapter.itemCount < 1){
                    memoryView.isVisible = false
                    noResults.isVisible = true
                }else{
                    noResults.isVisible = false
                }
            }

        }

        viewModel.currentPhotos.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

//    private fun setRecyclerViewScrollListener() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val totalItemcount = gridLayoutManager.itemCount
//                val lastitem = gridLayoutManager.findLastVisibleItemPosition()
//                Log.d(TAG, totalItemcount.toString())
//                Log.d(TAG, lastitem.toString())
//                // Figure out how to load more
//                if (totalItemcount <= (lastitem + 20)){
//                    Log.d(TAG, "Load More ")
//                    // TODO  It's loading far more than it should
//
//                    // Call only if it needs loading
//
////                    viewModel.nextPage()
//                }
//
//            }
//        }
//        mainrecycler.addOnScrollListener(scrollListener)
//    }

    // Will have to update the UI from this call
//    private fun bindUI() = launch{
////        val foundImages = viewModel.currentPhotos
//        viewModel.currentPhotos.observe(viewLifecycleOwner, Observer {
//            if (it == null) return@Observer
////            binding.textView.text = it.toString()
////            Log.d(TAG, "Gotten ${it.size} images")
////            adapter.addPhotos(it)
////            adapter.notifyDataSetChanged()
//            adapter.submitData(viewLifecycleOwner.lifecycle, it)
//        })
//    }




}