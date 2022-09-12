package com.example.callbackstarter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
@ConditionalOnProperty(prefix = "callback")
@EnableConfigurationProperties(value = [CallbackProperties::class])
class CallbackAutoConfiguration {

    @Autowired
    lateinit var callbackProperties: CallbackProperties

    @Bean
    fun callbackExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    @Bean
    @ConditionalOnMissingBean
    fun callbackManager(): CallbackManager {
        return CallbackManager(callbackExecutor())
    }

}