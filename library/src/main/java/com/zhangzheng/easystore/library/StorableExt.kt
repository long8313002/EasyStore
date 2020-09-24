package com.zhangzheng.easystore.library

import java.lang.reflect.Proxy
import kotlin.reflect.KClass

fun Storable.commit(build: IStoreBuilder = SharedPreferencesStore.BUILDER) {
    build.build(this).commit(this.generateMap())
}

fun Storable.apply(build: IStoreBuilder = SharedPreferencesStore.BUILDER) {
    build.build(this).apply(this.generateMap())
}


fun <T : Storable> T.fill(build: IStoreBuilder = SharedPreferencesStore.BUILDER): T {
    val values = build.build(this).getAll()
    fillMap(values)
    return this
}


fun <T : Storable> KClass<T>.load(): T {
    val storable = proxyStorable(java)
    storable.fill()
    return storable
}

fun <T : Storable> KClass<T>.commit(init: T.() -> Unit) {
    val storable = proxyStorable(java)
    storable.init()
    storable.commit()
}

private fun <T : Storable> proxyStorable(clazz: Class<T>): T {
    val mapValue = mutableMapOf<String, Any>()

    return Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz)) { proxy, method, args ->
        val methodName = method.name
        when {
            methodName.startsWith("set") -> {
                mapValue[methodName.substring(3)] = args[0]
            }
            methodName.startsWith("get") -> {
                return@newProxyInstance mapValue[methodName.substring(3)]
            }
            methodName == "generateMap" -> {
                return@newProxyInstance mapValue
            }
            methodName == "fillMap" -> {
                (args[0] as Map<String,Any>).forEach {
                    mapValue[it.key] = it.value
                }
            }
            else -> null
        }
    } as T
}
