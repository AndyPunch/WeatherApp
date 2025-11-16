package com.example.weatherapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.entity.DayType
import com.example.weatherapp.presentation.WeatherViewModel
import com.example.weatherapp.presentation.ui.theme.DarkBlue
import com.example.weatherapp.presentation.ui.theme.DeepBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: WeatherViewModel) {
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        viewModel.loadWeatherData()
        isRefreshing = false
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = state
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(
                    WindowInsets.systemBars.asPaddingValues()
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                WeatherScreen(
                    state = viewModel.state,
                    backgroundColor = DeepBlue
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecastScreen(state = viewModel.state, DayType.TODAY)
                Spacer(modifier = Modifier.height(32.dp))
                WeatherForecastScreen(state = viewModel.state, DayType.TOMORROW)
                Spacer(modifier = Modifier.height(32.dp))
                WeatherForecastScreen(state = viewModel.state, DayType.AFTER_TOMORROW)

            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            viewModel.state.error?.let { error ->
                ErrorScreen(viewModel, error)
            }
        }
    }
}
