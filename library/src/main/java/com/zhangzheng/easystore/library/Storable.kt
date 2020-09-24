package com.zhangzheng.easystore.library

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Store(val value: KClass<out IStoreBuilder>)


interface  Storable{
    fun generateMap(): Map<String, Any?>

    fun fillMap(values:Map<String, Any?>)
}