package com.portfolio.fakenewsapp

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity(), IJsoupData {

    private var loader: AsyncTask<Void, Void, ArrayList<New>>? = null
    private val WEB_PAGE: String = "limitstart=0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loader = LoadNews(this, WEB_PAGE)
        loader!!.execute()
    }

    override fun getWebData(datas: ArrayList<New>) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NEWS", datas)
            startActivity(intent)
            finish()
    }
}