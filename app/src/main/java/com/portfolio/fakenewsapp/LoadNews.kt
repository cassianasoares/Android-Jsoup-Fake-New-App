package com.portfolio.fakenewsapp

import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadNews(var activity: AppCompatActivity?, var page: String): AsyncTask<Void, Void, ArrayList<News>>(){

        private var news: ArrayList<News> = ArrayList()
        private var loadedData = activity as IJsoupData

        override fun doInBackground(vararg params: Void?): ArrayList<News> {
            try {
                val url = "https://www.saude.gov.br/fakenews?$page"
                val doc: Document = Jsoup.connect(url).get()
                //get images inside of the div
                val div: Elements = doc.select("div.tileImage")
                //get titles inside of the H2
                val tagHeading: Elements = doc.select("h2.tileHeadline")

                val times = doc.select("div.span3.tileInfo > ul")

                val size: Int = div.size
                Log.i("DIV", size.toString())
                for (index in 0..size-1){
                    //get image link inside tag "img" with attribute src
                    val imgUrl: String = div.select("img").eq(index).attr("src")
                    //get text title inside tag "a"
                    val title: String = tagHeading.select("a").eq(index).text()
                    //get details news link inside tag "a" with attribute "href"
                    val details: String = tagHeading.select("a").attr("href")

                    val datePost = times[index].select("li").get(1)
                    val timePost = times[index].select("li").get(2)

                    Log.i("Result", imgUrl + " " + title.replaceAfter("-", "").replace("-", "") + " " + details + " " + datePost.text())
                    news.add(News("https://www.saude.gov.br"+imgUrl,
                        title, details, datePost.text().trim(),
                        timePost.text().trim()))
                }

            }catch (e: IOException){
                e.printStackTrace()
            }
            return news
        }

        override fun onPostExecute(result: ArrayList<News>?) {
            loadedData.getWebData(result!!)
        }
}