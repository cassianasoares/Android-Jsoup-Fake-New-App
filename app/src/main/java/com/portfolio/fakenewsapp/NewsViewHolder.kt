package com.portfolio.fakenewsapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val img_news: ImageView = itemView.image_card
    val txt_title: TextView = itemView.txt_title

    fun bindView(news: News){
        txt_title.text = news.title
        Picasso.get().load(news.image).into(img_news)
    }

}