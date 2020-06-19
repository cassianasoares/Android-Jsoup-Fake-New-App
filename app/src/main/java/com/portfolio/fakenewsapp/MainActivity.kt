package com.portfolio.fakenewsapp

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ILoadMore, IJsoupData {

    private var news: ArrayList<News>? = ArrayList()
    private var newsLoad: MutableList<News?> = ArrayList()
    lateinit var newsAdapter: NewsAdapter
    private var loader: AsyncTask<Void, Void, ArrayList<News>>? = null
    private var numberPage: Int = 20
    private var WEB_PAGE: String? = null

    override fun onLoadMore() {
        WEB_PAGE = "start=$numberPage"
        loader = LoadNews(this, WEB_PAGE!!)
        loader!!.execute()
        numberPage+=10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        news = intent.getSerializableExtra("NEWS") as ArrayList<News>
        getTenNews(news!!)

        recyclerview.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(recyclerview, this, newsLoad)
        recyclerview.adapter = newsAdapter
        newsAdapter.getLoadMore(this)
    }

    private fun getTenNews(listNews: ArrayList<News>) {
        for (index in 0..9){
            newsLoad.add(listNews[index])
        }
    }

    override fun getWebData(datas: ArrayList<News>) {
        if (newsLoad.size < 50){
            newsLoad.add(null)
            newsAdapter.notifyItemInserted(newsLoad.size-1)

            Handler().postDelayed({
                newsLoad.removeAt(newsLoad.size-1)
                newsAdapter.notifyItemRemoved(newsLoad.size)

                getTenNews(datas)

                newsAdapter.notifyDataSetChanged()
                newsAdapter.setLoaded()

            }, 3000)
        } else {
            Toast.makeText(this, "Load data finish!", Toast.LENGTH_SHORT).show()
        }
    }

    fun callAbout(){
        startActivity(Intent(this, AboutActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.btn_about -> callAbout()
        }

        return super.onOptionsItemSelected(item)
    }

}
