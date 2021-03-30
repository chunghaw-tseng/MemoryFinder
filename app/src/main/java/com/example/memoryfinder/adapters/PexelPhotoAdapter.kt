package com.example.memoryfinder.adapters

import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memoryfinder.R
import com.example.memoryfinder.data.model.Photo

class PexelPhotoAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<Photo, PexelPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    private val TAG: String = "PexelAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.memory_card, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

   inner class PhotoViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val imgView: ImageView
        val authorText: TextView

        init {
            imgView = view.findViewById(R.id.pexelimage)
            authorText = view.findViewById(R.id.author)
            view.setOnClickListener(View.OnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onitemClick(item)
                    }
                }
            })
        }

        fun bind(photo: Photo) {
            authorText.text = photo.photographer
            Glide.with(itemView).load(photo.src.medium).centerCrop().error(R.drawable.load_error).into(imgView)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }

    }
}