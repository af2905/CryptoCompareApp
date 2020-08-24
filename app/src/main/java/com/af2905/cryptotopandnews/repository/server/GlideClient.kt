package com.af2905.cryptotopandnews.repository.server

import android.content.Context
import android.widget.ImageView
import com.af2905.cryptotopandnews.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideClient {
    companion object {
        fun downloadImage(context: Context, imgUrl: String, imgView: ImageView) {
            Glide.with(context).load(imgUrl).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error)
                    .fitCenter()
            ).transition(DrawableTransitionOptions.withCrossFade()).into(imgView)
        }
    }
}
