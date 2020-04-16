package com.portfolio.fakenewsapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILoadMore, IJsoupData {

    private var news: ArrayList<New>? = ArrayList()
    private var newsLoad: MutableList<New?> = ArrayList()
    lateinit var newAdapter: NewAdapter
    private var loader: AsyncTask<Void, Void, ArrayList<New>>? = null
    private var numberPage: Int = 0
    private var WEB_PAGE: String? = null

    override fun onLoadMore() {
        numberPage+=10
        WEB_PAGE = "start=$numberPage"
        loader = LoadNews(this, WEB_PAGE!!)
        loader!!.execute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        news = intent.getSerializableExtra("NEWS") as ArrayList<New>
        getTenNews(news!!)

        recyclerview.layoutManager = LinearLayoutManager(this)
        newAdapter = NewAdapter(recyclerview, this, newsLoad)
        recyclerview.adapter = newAdapter
        newAdapter.getLoadMore(this)
    }

    private fun getTenNews(listNews: ArrayList<New>) {
        for (index in 0..9){
            newsLoad.add(listNews[index])
        }
    }

    override fun getWebData(datas: ArrayList<New>) {
        if (newsLoad.size < 50){
            newsLoad.add(null)
            newAdapter.notifyItemInserted(newsLoad.size-1)

            Handler().postDelayed({
                newsLoad.removeAt(newsLoad.size-1)
                newAdapter.notifyItemRemoved(newsLoad.size)

                getTenNews(datas)

                newAdapter.notifyDataSetChanged()
                newAdapter.setLoaded()

            }, 3000)
        } else {
            Toast.makeText(this, "Load data finish!", Toast.LENGTH_SHORT).show()
        }
    }

}
