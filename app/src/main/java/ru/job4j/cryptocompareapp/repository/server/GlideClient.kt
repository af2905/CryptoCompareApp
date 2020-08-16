package ru.job4j.cryptocompareapp.repository.server

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.job4j.cryptocompareapp.R

class GlideClient {
    companion object {
        fun downloadImage(context: Context, imgUrl: String, imgView: ImageView) {
            Glide.with(context).load(imgUrl).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error)
            ).transition(DrawableTransitionOptions.withCrossFade()).into(imgView)
        }
    }
}
