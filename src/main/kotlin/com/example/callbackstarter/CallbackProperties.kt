package com.example.callbackstarter

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "callback")
data class CallbackProperties(
    val worker: Int = 1
)
