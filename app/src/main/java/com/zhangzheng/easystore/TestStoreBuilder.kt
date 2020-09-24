package com.zhangzheng.easystore

import android.content.Context
import com.zhangzheng.easystore.library.IStore
import com.zhangzheng.easystore.library.IStoreBuilder
import com.zhangzheng.easystore.library.Storable
import kotlin.reflect.KClass

class TestStoreBuilder : IStoreBuilder, IStore {

    override fun build(storable: KClass<out Storable>, context: Context): IStore {
        return this
    }

    private var cacheValues: Map<String, Any?>? = null

    override fun commit(values: Map<String, Any?>): Boolean {
        cacheValues = values
        return true
    }

    override fun apply(values: Map<String, Any?>) {
        cacheValues = values
    }

    override fun getAll(): Map<String, Any> {
        val values = mutableMapOf<String, Any>()
        cacheValues?.forEach {
            if (it.value != null) {
                values[it.key] = it.value!!
            }
        }
        return values
    }



}