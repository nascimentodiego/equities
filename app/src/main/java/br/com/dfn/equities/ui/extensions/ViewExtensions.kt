package br.com.dfn.equities.ui.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadUrl(url: String) {
    Glide.with(this.context).load(url).into(this)
}