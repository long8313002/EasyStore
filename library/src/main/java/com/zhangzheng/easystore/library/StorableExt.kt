package com.zhangzheng.easystore.library

import java.lang.reflect.Proxy
import kotlin.reflect.KClass

private fun Storable.commit(storage: IStore) {
    storage.commit(this.generateMap())
}

private fun Storable.apply(storage: IStore) {
    storage.apply(this.generateMap())
}

private fun <T : Storable> T.fill(storage: IStore): T {
    val values = storage.getAll()
    fillMap(values)
    return this
}

private val spStoreBuilder = SharedPreferencesStore.Builder()

private fun <T : Storable> KClass<T>.getStoreBuilder(): IStoreBuilder {
    val builder = java.getAnnotation(Store::class.java)?.value
    return if (builder == null) {
        spStoreBuilder
    } else {
        builder.java.newInstance()
    }
}

private fun <T : Storable> KClass<T>.getStorage() =
    getStoreBuilder().build(this, EasyStore.getContext())


fun <T : Storable> KClass<T>.load(): T {
    val storable = proxyStorable(java)
    storable.fill(getStorage())
    return storable
}

fun <T : Storable> KClass<T>.apply(init: T.() -> Unit) {
    val storable = proxyStorable(java)
    storable.init()
    storable.apply(getStorage())
}

fun <T : Storable> KClass<T>.commit(init: T.() -> Unit) {
    val storable = proxyStorable(java)
    storable.init()
    storable.commit(getStorage())
}

fun <T : Storable, M : Any> KClass<T>.get(get: T.() -> M): M {
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
                (args[0] as Map<String, Any>).forEach {
                    mapValue[it.key] = it.value
                }
            }
            else -> null
        }
    } as T
}
