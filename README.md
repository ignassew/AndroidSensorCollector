# Sensor Collector Library for Android
Collect sensor data and system information to a websocket server

# Usage

1. Install the library
2. [Add necessary permissions](#Permissions)
3. Initialize the Collector class when the app starts:
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Collector(application, "<server address>")
    // ...
}
```

# Permissions

### Internet

This library doesn't state any permissions in its manifest, but it does require internet access. You will need to add `android.permission.INTERNET`to your `AndroidManifest.xml`

### Cleartext traffic (optional)

If your WebSocket server is **not** behind TLS,
you will have to add `android:usesCleartextTraffic="true"` attribute
to [application](https://developer.android.com/guide/topics/manifest/application-element)
in `AndroidManifest.xml`.

# Server

There is no official server available for the collector.

If you want to build your own server, read the spec below:

### Message types

This library uses WebSockets to communicate with the server.
There are only 4 message types sent by the client.
Messages are encoded in JSON.
Server is not expected to ever send anything back.

<details>
  <summary>Identity</summary>

  This message has to be sent before any other message.
  Otherwise server shall close the connection.

  ```json
  {
    "messageType": "identity",
    "packageName": "com.ignassew.myapplication"
  }
  ```

</details>
<details>
  <summary>System Info</summary>
  
  ```json
  {
    "build": {
      "board": "string",
      "bootloader": "string",
      "brand": "string",
      "device": "string",
      "display": "string",
      "fingerprint": "string",
      "hardware": "string",
      "host": "string",
      "id": "string",
      "manufacturer": "string",
      "model": "string",
      "product": "string",
      "tags": "string",
      "type": "string",
      "user": "string",
      "version": {
        "codename": "string",
        "incremental": "string",
        "release": "string",
        "sdk_int": "int"
      }
    },
    "id": "string",
    "messageType": "systemInfo",
    "perfBench": "list<string>",
    "screen": {
      "density": "float",
      "heightPixels": "int",
      "widthPixels": "int"
    },
    "sensorList": "list<string>"
  }
  ```
      
</details>
<details>
  <summary>Sensor</summary>

  ```json
  {
    "accuracy": "int",
    "messageType": "sensor",
    "timestamp": "int (nanoseconds)",
    "type": "int",
    "x": "float",
    "y": "float",
    "z": "float"
  }
  
  ```

</details>
<details>
  <summary>Error</summary>
    
  ```json
  {
    "messageType": "error",
    "message": "string"
  }
  ```

</details>
