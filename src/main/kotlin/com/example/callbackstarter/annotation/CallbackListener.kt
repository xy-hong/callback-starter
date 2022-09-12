package com.example.callbackstarter.annotation

import com.example.callbackstarter.CallbackManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ConditionalOnProperty(prefix = "broker", value = ["ctp"])
@Import(value = [CallbackManager::class])
annotation class CallbackListener()
