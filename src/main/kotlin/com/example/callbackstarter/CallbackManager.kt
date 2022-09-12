package com.example.callbackstarter

import com.example.callbackstarter.annotation.CallbackListener
import com.example.callbackstarter.annotation.Callback
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import java.lang.reflect.Method
import java.util.concurrent.ExecutorService
import kotlin.RuntimeException

class CallbackManager(
    private val callbackExecutor: ExecutorService
) : ApplicationContextAware, SmartInitializingSingleton {

    lateinit var context: ApplicationContext

    var callbackMap = mutableMapOf<String, CallbackWrapper>()

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    override fun afterSingletonsInstantiated() {
        val beanList = context.getBeansWithAnnotation(CallbackListener::class.java).values.toList()
        beanList.forEach { bean ->
            val clazz = AopProxyUtils.ultimateTargetClass(bean)
            clazz.methods.forEach { method ->
                if (method.isAnnotationPresent(Callback::class.java)) {
                    this.register(method.name, CallbackWrapper(bean, method))
                }
            }
        }
    }

    fun register(name: String, callbackWrapper: CallbackWrapper) {
        if (callbackMap[name] == null) {
            callbackMap[name] = callbackWrapper
        }

        throw RuntimeException("已经存在该方法")
    }

    fun call(name: String, vararg args: Any) {
        val wrapper = callbackMap[name]
        if (wrapper != null) {
            callbackExecutor.execute {
                wrapper.method.invoke(wrapper.obj, *args)
            }
        }

        throw RuntimeException("不存在该方法")
    }
}

data class CallbackWrapper(
    val obj: Any,
    val method: Method
)