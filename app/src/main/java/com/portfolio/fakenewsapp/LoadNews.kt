package com.portfolio.fakenewsapp

import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadNews(var activity: AppCompatActivity?, var page: String): AsyncTask<Void, Void, ArrayList<New>>(){

        private var news: ArrayList<New> = ArrayList()
        private var loadedData = activity as IJsoupData

        override fun doInBackground(vararg params: Void?): ArrayList<New> {
            try {
                val url = "https://www.saude.gov.br/fakenews?$page"
                val doc: Document = Jsoup.connect(url).get()
                //get images inside of the div
                val div: Elements = doc.select("div.tileImage")
                //get titles inside of the H2
                val tagHeading: Elements = doc.select("h2.tileHeadline")

                val size: Int = div.size
                for (index in 0..size){
                    //get image link inside tag "img" with attribute src
                    val imgUrl: String = div.select("img").eq(index).attr("src")
                    //get text title inside tag "a"
                    val title: String = tagHeading.select("a").eq(index).text()
                    //get details news link inside tag "a" with attribute "href"
                    val details: String = tagHeading.select("a").attr("href")

                    Log.i("Result", imgUrl + " " + title + " " + details)
                    news.add(New("https://www.saude.gov.br"+imgUrl, title, details))
                }

            }catch (e: IOException){
                e.printStackTrace()
            }
            return news
        }

        override fun onPostExecute(result: ArrayList<New>?) {
            loadedData.getWebData(result!!)
        }
}