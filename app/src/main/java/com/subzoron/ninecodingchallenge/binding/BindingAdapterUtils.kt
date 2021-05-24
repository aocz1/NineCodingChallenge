package com.subzoron.ninecodingchallenge.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.subzoron.ninecodingchallenge.R

object BindingAdapterUtils {
    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.show_placeholder)
            .into(view)
    }
}