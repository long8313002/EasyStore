package com.zhangzheng.easystore.library

import java.lang.reflect.Proxy
import kotlin.reflect.KClass

private fun Storable.commit() {
    getStoreBuilder().build(this,EasyStore.getContext()).commit(this.generateMap())
}

private fun Storable.apply() {
    getStoreBuilder().build(this,EasyStore.getContext()).apply(this.generateMap())
}

private fun <T : Storable> T.fill(): T {
    val values = getStoreBuilder().build(this,EasyStore.getContext()).getAll()
    fillMap(values)
    return this
}

private val spStoreBuilder = SharedPreferencesStore.Builder()

private fun  <T : Storable> T.getStoreBuilder():IStoreBuilder{
    return spStoreBuilder
}


fun <T : Storable> KClass<T>.load(): T {
    val storable = proxyStorable(java)
    storable.fill()
    return storable
}

fun <T : Storable> KClass<T>.apply(init: T.() -> Unit) {
    val storable = proxyStorable(java)
    storable.init()
    storable.apply()
}

fun <T : Storable> KClass<T>.commit(init: T.() -> Unit) {
    val storable = proxyStorable(java)
    storable.init()
    storable.commit()
}

fun <T : Storable,M:Any> KClass<T>.get(get: T.() -> M):M{
    val storable = load()
    return storable.get()
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
