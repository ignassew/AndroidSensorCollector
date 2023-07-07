package com.ignassew.sensorcollectorlibrary

internal class SendableError(error: Exception) {
    val messageType = "error"
    val message = error.message
}