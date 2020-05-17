package com.dantanini.ajhomework.ui.weathers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dantanini.ajhomework.R
import com.dantanini.ajhomework.model.Weather

private const val TYPE_WEATHER = 0
private const val TYPE_IMAGE = 1

class WeathersAdapter(private val weathers: List<Weather>, private val navigate: (weather: Weather) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (isEven(position)) TYPE_WEATHER else TYPE_IMAGE
    }

    private fun isEven(position: Int): Boolean {
        return position % 2 == 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId = if (viewType == TYPE_WEATHER) R.layout.layout_weather_item else R.layout.layout_image_item
        val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return if (viewType == TYPE_WEATHER) WeatherViewHolder(itemView) else ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == TYPE_WEATHER) {
            val weatherViewHolder = holder as WeatherViewHolder
            val realPosition = position / 2
            val weather = weathers[realPosition]
            weatherViewHolder.bind(weathers[realPosition])
            weatherViewHolder.itemView.setOnClickListener { navigate.invoke(weather) }
        }
    }

    override fun getItemCount(): Int {
        return weathers.size * 2
    }
}