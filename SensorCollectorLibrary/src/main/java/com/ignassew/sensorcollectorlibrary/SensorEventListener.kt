package com.ignassew.sensorcollectorlibrary

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager;
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.google.gson.Gson

internal class SensorEventListener(
    application: Application,
    private val webSocketClient: WebSocketClient,
    private val sensorsToListen: List<Int>
) : SensorEventListener {
    private val sensorManager =
        application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var lastSensors: HashMap<Int, SendableSensorEvent> = HashMap()
    private var eventsSent: HashMap<Int, Int> = HashMap()

    fun startListening() {
        val handlerThread = HandlerThread("sensorEventListener")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        for (type in sensorsToListen) {
            val defaultSensor = sensorManager.getDefaultSensor(type);
            sensorManager.registerListener(
                this,
                defaultSensor,
                SensorManager.SENSOR_DELAY_NORMAL,
                handler
            )
        }
    }

    private fun send(event: SendableSensorEvent) {
        Log.v("SensorCollector", "Sending sensor event $event")
        webSocketClient.sendMessage(Gson().toJson(event));
    }

    private fun stop() {
        sensorManager.unregisterListener(this)
        webSocketClient.close()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val sendableEvent = SendableSensorEvent(event);
        val lastEventTimestamp = this.lastSensors[sendableEvent.type]?.timestamp ?: 0

        if (sendableEvent.timestamp - lastEventTimestamp >= 100e6) {
            send(sendableEvent)
            this.lastSensors[sendableEvent.type] = sendableEvent

            val newCount: Int = (eventsSent[sendableEvent.type] ?: 0) + 1
            eventsSent[sendableEvent.type] = newCount
        }

        if (eventsSent.values.min() > 1024) {
            this.stop()
        }

    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {
    }
}

internal data class SendableSensorEvent(
    val messageType: String,
    val timestamp: Long,
    val accuracy: Int,
    val type: Int,
    val x: Float,
    val y: Float,
    val z: Float
) {
    constructor(sensorEvent: SensorEvent) : this(
        "sensor",
        sensorEvent.timestamp,
        sensorEvent.accuracy,
        sensorEvent.sensor.type,
        sensorEvent.values[0],
        sensorEvent.values[1],
        sensorEvent.values[2]
    )
}
