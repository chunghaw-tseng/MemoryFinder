package com.example.memoryfinder.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memoryfinder.R
import com.example.memoryfinder.data.model.Photo

class RecyclerAdapter(
    val context: Context
    ) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    // Save the list here and then

    private val TAG:String = "Adapter"
    private val mOnClickListener: View.OnClickListener = MyOnClickListener()
    private var photoList : List<Photo>

    init {
        photoList = emptyList()
    }


    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgView : ImageView
        val authorText : TextView

        init {
            imgView = view.findViewById(R.id.pexelimage)
            authorText = view.findViewById(R.id.author)

            view.setOnClickListener(View.OnClickListener {
                Log.d(TAG, "Clicked")
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.image_dialog)
                val progress = dialog.findViewById(R.id.progress_circular) as ProgressBar
                val body = dialog.findViewById(R.id.fullImage) as ImageView
                val current = photoList[adapterPosition]
                Glide.with(it).load(current.src.original).listener(object : RequestListener<Drawable>{
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
                        progress.visibility = View.GONE
                        body.visibility = View.VISIBLE
                        return false
                    }
                }).into(body)
                dialog.show()
            })
        }

        fun bind(photo: Photo){
            // Bind the data to the elements
            authorText.text = photo.photographer
            Glide.with(itemView).load(photo.src.small).into(imgView)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.PhotoHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.memory_card, parent, false)
//        view.setOnClickListener(mOnClickListener)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(photoList.get(position))

    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun addPhotos(photos : List<Photo>){
        photoList = photos
    }


   inner class MyOnClickListener : View.OnClickListener {
       override fun onClick(v: View?) {

       }
    }

}


