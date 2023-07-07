package com.ignassew.sensorcollectorlibrary

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest


internal class SystemInfo(application: Application) {
    val id = sha256Hash(Settings.Secure.ANDROID_ID + Build.FINGERPRINT)
    val messageType = "systemInfo"
    val build = com.ignassew.sensorcollectorlibrary.Build()
    var perfBench = PerfBench().benchmarkMultiple(10)
    val screen = Screen(application)
    val sensorList: List<Int> = getAvailableSensors(application)

    private fun getAvailableSensors(application: Application): List<Int> {
        val sensorManager: SensorManager =
            application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return sensorManager.getSensorList(Sensor.TYPE_ALL).map { sensor: Sensor -> sensor.type }
    }
}

internal class Build {
    val manufacturer: String = Build.MANUFACTURER
    val hardware: String = Build.HARDWARE
    val model: String = Build.MODEL
    val bootloader: String = Build.BOOTLOADER
    val version = Version()
    val product: String = Build.PRODUCT
    val tags: String = Build.TAGS
    val type: String = Build.TYPE
    val user: String = Build.USER
    val display: String = Build.DISPLAY
    val board: String = Build.BOARD
    val brand: String = Build.BRAND
    val device: String = Build.DEVICE
    val fingerprint: String = Build.FINGERPRINT
    val host: String = Build.HOST
    val id: String = Build.ID
}

internal class Version {
    val release: String = Build.VERSION.RELEASE
    val codename: String = Build.VERSION.CODENAME
    val incremental: String = Build.VERSION.INCREMENTAL
    val sdk_int: Int = Build.VERSION.SDK_INT
}

internal class Screen(application: Application) {
    val heightPixels: Int = application.resources.displayMetrics.heightPixels
    val widthPixels: Int = application.resources.displayMetrics.widthPixels
    val density: Float = application.resources.displayMetrics.density
}

fun sha256Hash(input: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(input.toByteArray())

    // Convert the byte array to a hexadecimal string
    val hexString = StringBuilder()
    for (byte in hashBytes) {
        val hex = String.format("%02x", byte)
        hexString.append(hex)
    }

    return hexString.toString()
}
