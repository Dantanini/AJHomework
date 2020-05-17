package com.dantanini.ajhomework.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dantanini.ajhomework.R
import com.dantanini.ajhomework.ui.MyConstants

class InfoFragment : Fragment() {
    private var startTime: String? = null
    private var endTime: String? = null
    private var temperature: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            startTime = it.getString(MyConstants.START_TIME)
            endTime = it.getString(MyConstants.END_TIME)
            temperature = it.getString(MyConstants.TEMPERATURE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        val infoView = root.findViewById<TextView>(R.id.info)
        infoView.text = getInfoString()
        return root
    }

    private fun getInfoString() = "$startTime\n$endTime\n$temperature"
}
