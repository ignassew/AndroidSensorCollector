package com.ignassew.sensorcollectorlibrary

import okhttp3.*
import okio.ByteString

class WebSocketClient(private val url: String) {
    private var webSocket: WebSocket? = null

    fun start() {
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // println("WebSocket connection established.")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // println("Received message: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // println("Received bytes: ${bytes.hex()}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                // println("WebSocket closing. Code: $code, Reason: $reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // println("WebSocket closed. Code: $code, Reason: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // println("WebSocket failure: ${t.message}")
            }
        })
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, null)
    }
}