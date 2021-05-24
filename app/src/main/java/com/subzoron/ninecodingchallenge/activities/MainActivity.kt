package com.subzoron.ninecodingchallenge.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.subzoron.ninecodingchallenge.R
import com.subzoron.ninecodingchallenge.databinding.ActivityMainBinding
import com.subzoron.ninecodingchallenge.datamodels.Payload
import com.subzoron.ninecodingchallenge.network.ShowsInterface
import com.subzoron.ninecodingchallenge.rcadapters.ShowsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val context = this
    var TAG = "mainactivity"
    var showsLoaded = false
    var listPayload = listOf<Payload>()
    private val showsInterface by lazy {
        ShowsInterface.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        loadFromServer()

        binding.btnMainRetry.setOnClickListener{
            loadFromServer()
        }

        binding.searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchShows()
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            }
        )

        binding.searchMain.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                binding.rcMain.adapter = ShowsAdapter(listPayload, context)
                (binding.rcMain.adapter as ShowsAdapter).notifyDataSetChanged()
                return false
            }
        })
    }

    fun searchShows(){
        val searchTerm = binding.searchMain.query.toString().toLowerCase(Locale.ROOT)
        val searchedList = listPayload.filter { p ->
            p.title.toLowerCase(Locale.ROOT).contains(searchTerm)  ||
                    p.genre.toLowerCase(Locale.ROOT).contains(searchTerm) ||
                    p.description.toLowerCase(Locale.ROOT).contains(searchTerm) }
        binding.rcMain.adapter = ShowsAdapter(searchedList, context)
        (binding.rcMain.adapter as ShowsAdapter).notifyDataSetChanged()
    }

    fun loadFromServer() {
        binding.pbMain.visibility = View.VISIBLE
        binding.layoutMainLoadFailed.visibility = View.GONE
        showsLoaded = false

        showsInterface.getShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .subscribe({
                    listPayload = it.payload
                    listPayload = listPayload.filter { p ->  p.episodeCount > 0 && p.drm == true}
                    val linearLayoutManager = LinearLayoutManager(context)
                    binding.rcMain.layoutManager = linearLayoutManager
                    binding.rcMain.adapter = ShowsAdapter(listPayload, context)
                    binding.pbMain.visibility = View.GONE
                    showsLoaded = true
                },
                {
                    binding.pbMain.visibility = View.INVISIBLE
                    binding.layoutMainLoadFailed.visibility = View.VISIBLE
                    Log.e(TAG, it.message!!)
                })
    }
}
