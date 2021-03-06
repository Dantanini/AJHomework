package com.dantanini.ajhomework.model

data class Weather(val endTime: String, val parameter: Parameter, val startTime: String) {
    val temperature get() = parameter.parameterName + parameter.parameterUnit
}