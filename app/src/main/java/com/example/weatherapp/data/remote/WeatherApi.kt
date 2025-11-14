package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.WeatherDto
import retrofit2.http.GET

interface WeatherApi {
    @GET("v1/forecast.json?key=fa8b3df74d4042b9aa7135114252304&q=55.7569,37.6151&days=3&lang=ru")
    suspend fun getWeatherData(): WeatherDto
}