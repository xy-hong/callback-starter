package com.example.callbackstarter.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Callback(
    val name: String=""
)

