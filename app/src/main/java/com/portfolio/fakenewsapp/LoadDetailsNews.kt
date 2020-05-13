package com.portfolio.fakenewsapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class LoadDetailsNews(activity: AppCompatActivity, var urlDetails: String):
    AsyncTask<Void, Void, ArrayList<String>>() {

    private var details: ArrayList<String>? = ArrayList()
    private var loader = activity as ILoadDetails

    override fun doInBackground(vararg params: Void?): ArrayList<String> {
        try{
            val baseUrl = "https://www.saude.gov.br"
            val url = baseUrl+urlDetails

            val doc: Document = Jsoup.connect(url).get()
            val div: Elements = doc.select("div.item-pagenoticias")
            div.select("div").remove()
            //get only the text inside the tags "p" and "li"
            val textComplete = div.select("p, ul > li")

            for (element in textComplete){
                if (element.text() != "")
                    details!!.add(element.text())
            }

        }catch (e: IOException){
            e.printStackTrace()
        }
        return details!!
    }

    override fun onPostExecute(result: ArrayList<String>?) {
        loader.getDetails(result!!)
    }

}