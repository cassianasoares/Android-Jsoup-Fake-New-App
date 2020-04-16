package com.portfolio.fakenewsapp

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_loader.view.*

class LoadViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val progressbar: ProgressBar = itemView.progressBar

    fun bindView(){
        progressbar.isIndeterminate = true
    }

}