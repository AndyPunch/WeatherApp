package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.entity.WeatherData
import com.example.weatherapp.domain.util.Resource

interface WeatherRepository {
    suspend fun getWeatherData(): Resource<WeatherData>
}