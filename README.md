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


# What data is being collected

All data being collected is anonymous.

### Device data
- [Manufacturer](https://developer.android.com/reference/android/os/Build#MANUFACTURER)
- [Hardware](https://developer.android.com/reference/android/os/Build#HARDWARE)
- [Model](https://developer.android.com/reference/android/os/Build#MODEL)
- [Bootloader](https://developer.android.com/reference/android/os/Build#BOOTLOADER)
- [Product](https://developer.android.com/reference/android/os/Build#PRODUCT)
- [Tags](https://developer.android.com/reference/android/os/Build#TAGS)
- [Type](https://developer.android.com/reference/android/os/Build#TYPE)
- [User](https://developer.android.com/reference/android/os/Build#USER)
- [Display](https://developer.android.com/reference/android/os/Build#DISPLAY)
- [Board](https://developer.android.com/reference/android/os/Build#BOARD)
- [Brand](https://developer.android.com/reference/android/os/Build#BRAND)
- [Device](https://developer.android.com/reference/android/os/Build#DEVICE)
- [Fingerprint](https://developer.android.com/reference/android/os/Build#FINGERPRINT)
- [Host](https://developer.android.com/reference/android/os/Build#HOST)
- [Id](https://developer.android.com/reference/android/os/Build#ID)
- Version
  * [Release](https://developer.android.com/reference/android/os/Build.VERSION#RELEASE)
  * [Codename](https://developer.android.com/reference/android/os/Build.VERSION#CODENAME)
  * [Incremental](https://developer.android.com/reference/android/os/Build.VERSION#INCREMENTAL)
  * [Sdk_int](https://developer.android.com/reference/android/os/Build.VERSION#SDK_INT)
- Screen
  * width
  * height
  * pixel density
- List of available sensors
- Performance micro-benchmarks (e.g. square root of a number ran n times)
- SHA-256 hash of [ANDROID_ID](https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID) and [Build.FINGERPRINT](https://developer.android.com/reference/android/os/Build#FINGERPRINT) combined

### Sensor data

To prevent battery drain, this library will collect at least 128 events of each type and then turn off. This takes around 30 seconds.

This library collects only following sensor events:
- [Accelerometer](https://developer.android.com/reference/android/hardware/Sensor#TYPE_ACCELEROMETER)
- [Gravity](https://developer.android.com/reference/android/hardware/Sensor#TYPE_GRAVITY)
- [Magnetic Field](https://developer.android.com/reference/android/hardware/Sensor#TYPE_MAGNETIC_FIELD)
- [Gyroscope](https://developer.android.com/reference/android/hardware/Sensor#TYPE_GYROSCOPE)


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

# License


Copyright 2023 Ignacy Sewastianowicz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
