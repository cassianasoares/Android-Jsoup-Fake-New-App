package com.portfolio.fakenewsapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(recyclerView: RecyclerView, var activity: Activity, var news: MutableList<News?>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   val VIEW_TYPE_ITEM = 0
    val VIEW_TYPE_LOADING = 1
    val visibleThreshold = 5
    var loadMore: ILoadMore? = null
    var isLoading: Boolean = false
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                    if (loadMore != null){
                        loadMore!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        if (news[position] == null){
            return VIEW_TYPE_LOADING
        }else{
            return VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        var view: View? = null
        if (viewType == VIEW_TYPE_ITEM){
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news, parent, false)
            viewHolder = NewsViewHolder(view)
        } else if (viewType == VIEW_TYPE_LOADING){
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loader, parent, false)
            viewHolder = LoadViewHolder(view)
        }
        return viewHolder!!
    }

    fun getLoadMore(iLoaded: ILoadMore){
        this.loadMore = iLoaded
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.bindView(news[position]!!)
        } else if (holder is LoadViewHolder) {
            holder.bindView()
        }

        val newsItem = news[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, DetailsNewsActivity::class.java)
            intent.putExtra("IMAGE", newsItem!!.image)
            intent.putExtra("TITLE", newsItem.title)
            intent.putExtra("DETAILS", newsItem.details)
            intent.putExtra("DATE", newsItem.date)
            intent.putExtra("TIME", newsItem.time)
            activity.startActivity(intent)
        }

    }

    fun setLoaded(){
        isLoading = false
    }

}