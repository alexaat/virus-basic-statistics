package com.alexaat.virusbasicstatistics.adapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.alexaat.virusbasicstatistics.R
import com.alexaat.virusbasicstatistics.network.CountryStats
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

//https://www.countryflags.io/be/flat/64.png
//https://www.countryflags.io/be/shiny/64.png

@BindingAdapter("setFlatFlag")
fun ImageView.setFlatFlag(countryStats: CountryStats){
    if(countryStats.CountryCode=="GLOBAL"){
        this.setImageResource(R.drawable.globe_icon_64)
    }else {
        val url =
            "https://www.countryflags.io/" + countryStats.CountryCode.toLowerCase(Locale.UK) + "/flat/64.png"
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        this?.let { v ->
            Glide.with(v.context)
                .load(imgUri)
                .placeholder(R.drawable.loading_spinner)
                .error(R.drawable.ic_broken_image)
                .into(v)
        }
    }
}

@BindingAdapter("setCountryName")
fun TextView.setCountryName(countryStats: CountryStats){
    if(countryStats.CountryCode=="GLOBAL"){
        text = resources.getString(R.string.global)
        return
    }
    text = countryStats.Country
}

@BindingAdapter("setTotalConfirmed")
fun TextView.setTotalConfirmed(countryStats: CountryStats){
    text = resources.getString(R.string.stats_formatted, countryStats.TotalConfirmed, countryStats.NewConfirmed)

}

@BindingAdapter("setTotalDeaths")
fun TextView.setTotalDeaths(countryStats: CountryStats){
    text = resources.getString(R.string.stats_formatted, countryStats.TotalDeaths, countryStats.NewDeaths)

}

@BindingAdapter("setDate")
fun TextView.setDate(countryStats: CountryStats){
    val sdfOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var newDate:String
    try {
        val date = sdfOriginal.parse(countryStats.Date)
        val sdf = SimpleDateFormat("dd-MMM-YYYY HH:mm:ss", Locale.UK)
        val formattedDate = sdf.format(date)
        newDate = formattedDate
    }catch(e:Exception){
        newDate = "parsing problem"
    }
    text = newDate
}
