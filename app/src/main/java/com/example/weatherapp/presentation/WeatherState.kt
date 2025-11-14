package com.example.weatherapp.presentation

import com.example.weatherapp.domain.entity.WeatherData


data class WeatherState(
    val weatherInfo: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
