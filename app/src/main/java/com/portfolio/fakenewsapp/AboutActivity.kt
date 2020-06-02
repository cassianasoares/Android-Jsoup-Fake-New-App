package com.portfolio.fakenewsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator ( R.drawable.ic_back )

        clickedCardOpenLink()
    }

    private fun clickedCardOpenLink() {
        card_github.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cassianasoares"))
            startActivity(browserIntent)
        }
        card_youtube.setOnClickListener {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/channel/UCuqZ5zPwl5zmtrclR6EtTlg/")
                )
            startActivity(browserIntent)
        }
        card_wordpress.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://devmundi.tech.blog"))
            startActivity(browserIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }



}
