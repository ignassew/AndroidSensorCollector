package com.ignassew.sensorcollectorlibrary

import android.app.Application
import android.hardware.Sensor
import com.google.gson.Gson
import kotlin.Exception
import kotlin.concurrent.thread

class Collector(application: Application, url: String) {
    private val sensorsToListen = listOf(
        Sensor.TYPE_ACCELEROMETER,
        Sensor.TYPE_GRAVITY,
        Sensor.TYPE_MAGNETIC_FIELD,
        Sensor.TYPE_GYROSCOPE
    )

    private var webSocketClient = WebSocketClient(url)
    private var sensorEventListener = SensorEventListener(
        application,
        webSocketClient,
        sensorsToListen
    )

    init {
        webSocketClient.start()

        try {
            webSocketClient.sendMessage(Gson().toJson(SendableIdentity(application)))

            sensorEventListener.startListening()
            thread {
                val systemInfo = SystemInfo(application)
                webSocketClient.sendMessage(Gson().toJson(systemInfo))
            }
        } catch (e: Exception) {
            val sendableError = SendableError(e)
            webSocketClient.sendMessage(Gson().toJson(sendableError))
        }
    }
}
