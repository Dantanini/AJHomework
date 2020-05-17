package com.dantanini.ajhomework.ui.weathers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dantanini.ajhomework.BuildConfig
import com.dantanini.ajhomework.R
import com.dantanini.ajhomework.api.WeatherManager
import com.dantanini.ajhomework.model.Weather
import kotlinx.coroutines.*

class WeathersFragment : Fragment() {
    private val errorHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private val scope = CoroutineScope(Dispatchers.IO + errorHandler)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_weathers, container, false)
        initRecyclerView(root)
        return root
    }

    private fun initRecyclerView(root: View) {
        val recyclerView: RecyclerView = root.findViewById(R.id.weathers)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        getWeathersAndInitAdapter(root, recyclerView)
    }

    private fun getWeathersAndInitAdapter(root: View, recyclerView: RecyclerView) {
        scope.launch {
            val manager = WeatherManager(BuildConfig.API_KEY)
            val weathers = manager.getTaipeiWeathers()
            withContext(Dispatchers.Main) {
                val adapter = WeathersAdapter(weathers) { weather -> navigateByWeather(root, weather) }
                recyclerView.adapter = adapter
            }
        }
    }

    private fun navigateByWeather(root: View, weather: Weather) {
        val bundle = bundleOf("startTime" to weather.startTime, "endTime" to weather.endTime, "temperature" to weather.temperature)
        Navigation.findNavController(root).navigate(R.id.action_weathers_to_info, bundle)
    }
}
