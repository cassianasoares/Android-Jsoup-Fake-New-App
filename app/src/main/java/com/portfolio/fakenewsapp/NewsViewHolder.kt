package com.portfolio.fakenewsapp

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val img_news: ImageView = itemView.image_card
    val txt_title: TextView = itemView.txt_title
    val txt_isfake: TextView = itemView.txt_isfake

    fun bindView(news: News){
        txt_title.text = news.title.replaceAfter("-", "")
            .replace("-", "")

        if(news.title.contains("FAKE")){
            txt_isfake.setTextColor(Color.parseColor("#973C57"))
            txt_isfake.text = "É FAKE NEWS"
        }else if(news.title.contains("VERDADE")){
            txt_isfake.setTextColor(Color.parseColor("#63C363"))
            txt_isfake.text = "É VERDADE"
        }

        Picasso.get().load(news.image).into(img_news)
    }

}