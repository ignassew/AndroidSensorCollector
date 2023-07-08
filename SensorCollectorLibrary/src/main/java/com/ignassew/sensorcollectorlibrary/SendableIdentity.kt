package com.ignassew.sensorcollectorlibrary

import android.app.Application

class SendableIdentity(application: Application) {
    val messageType = "identity"
    val packageName = application.packageName
}