package com.alexaat.virusbasicstatistics.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexaat.virusbasicstatistics.R
import com.alexaat.virusbasicstatistics.adapters.CountryListAdapter.*
import com.alexaat.virusbasicstatistics.databinding.CountryListItemBinding
import com.alexaat.virusbasicstatistics.network.CountryStats


class CountryListAdapter:ListAdapter<CountryStats, CountryListViewHolder>(CountryListCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder = CountryListViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) = holder.bind(getItem(position))
    class CountryListViewHolder(private val binding: CountryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun inflateFrom(parent: ViewGroup): CountryListViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val res = R.layout.country_list_item
                return CountryListViewHolder(DataBindingUtil.inflate(inflater,res,parent,false))
            }
        }

        fun bind(countryStats: CountryStats){
            binding.countryStats = countryStats
            binding.executePendingBindings()
        }
    }
}

class CountryListCallBack: DiffUtil.ItemCallback<CountryStats>() {
    override fun areItemsTheSame(oldItem: CountryStats, newItem: CountryStats): Boolean {
        return oldItem.Country == newItem.Country
    }
    override fun areContentsTheSame(oldItem: CountryStats, newItem: CountryStats): Boolean {
        return oldItem == newItem
    }
}