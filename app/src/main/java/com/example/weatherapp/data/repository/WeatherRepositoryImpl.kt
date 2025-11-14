package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mappers.toWeatherData
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.entity.WeatherData
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherData(): Resource<WeatherData> {
        return try {
            Resource.Success(
                data = api.getWeatherData().toWeatherData()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }

    }
}