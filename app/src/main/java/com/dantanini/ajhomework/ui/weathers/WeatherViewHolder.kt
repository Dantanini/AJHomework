package com.dantanini.ajhomework.ui.weathers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dantanini.ajhomework.R
import com.dantanini.ajhomework.model.Weather

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val startTimeView: TextView = itemView.findViewById(R.id.weather_start_time)
    private val endTimeView: TextView = itemView.findViewById(R.id.weather_end_time)
    private val temperatureView: TextView = itemView.findViewById(R.id.weather_temperature)

    fun bind(weather: Weather) {
        startTimeView.text = weather.startTime
        endTimeView.text = weather.endTime
        temperatureView.text = weather.temperature
    }
}