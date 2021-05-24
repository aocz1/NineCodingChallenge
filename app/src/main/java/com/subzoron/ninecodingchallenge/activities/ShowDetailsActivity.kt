package com.subzoron.ninecodingchallenge.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.subzoron.ninecodingchallenge.R
import com.subzoron.ninecodingchallenge.databinding.ActivityShowDetailsBinding
import com.subzoron.ninecodingchallenge.datamodels.Payload

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_details)

        val gson = Gson()
        val payload = gson.fromJson<Payload>(intent.getStringExtra("payload"), Payload::class.java)
        binding.payload = payload

        setSupportActionBar(binding.tbShowDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}