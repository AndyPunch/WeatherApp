package com.example.weatherapp.presentation.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.weatherapp.R
import com.example.weatherapp.presentation.WeatherViewModel

@Composable
fun ErrorScreen(viewModel: WeatherViewModel, error: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var showDialog by remember { mutableStateOf(true) }
        val context = LocalContext.current
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = stringResource(R.string.error))
                },
                text = {
                    Text(error)
                },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        viewModel.loadWeatherData()
                    }) {
                        Text(stringResource(R.string.retry))
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDialog = false
                        (context as? Activity)?.finish()
                    }) {
                        Text(stringResource(R.string.exit))
                    }
                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            )
        }
    }
}