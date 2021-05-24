package com.subzoron.ninecodingchallenge.rcadapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.subzoron.ninecodingchallenge.R
import com.subzoron.ninecodingchallenge.activities.ShowDetailsActivity
import com.subzoron.ninecodingchallenge.databinding.RcitemShowBinding
import com.subzoron.ninecodingchallenge.datamodels.Payload

class ShowsAdapter(private var dataSet: List<Payload>, val context: Context) :
    RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    fun getDataset() : List<Payload>{
        return dataSet
    }

    fun setDataset(newDataset: List<Payload>){
        dataSet = newDataset
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = RcitemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    class ViewHolder(val binding: RcitemShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Payload) = with(binding) {
            binding.payload = p
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataSet[position].let { holder.bind(it) }

        holder.binding.layoutRcItemShow.setOnClickListener{
            val intent = Intent(context, ShowDetailsActivity::class.java)
            val gson = Gson()
            intent.putExtra("payload", gson.toJson(dataSet[position]))
            context.startActivity(intent)
        }
    }
}