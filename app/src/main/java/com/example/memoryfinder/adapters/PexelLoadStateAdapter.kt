package com.example.memoryfinder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryfinder.databinding.PhotoLoadStateFooterBinding

class PexelLoadStateAdapter(private val retry: () -> Unit)
    : LoadStateAdapter<PexelLoadStateAdapter.LoadStateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = PhotoLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


   inner class LoadStateViewHolder(private val binding: PhotoLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryBtn.isVisible = loadState !is LoadState.Loading
                errorMessage.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}