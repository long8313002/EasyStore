package com.zhangzheng.easystore.library

import android.content.Context
import android.content.SharedPreferences


class SharedPreferencesStore(var name: String) : IStore {

   private val sp = EasyStore.getContext().getSharedPreferences(name,Context.MODE_PRIVATE)

    companion object {
        val BUILDER: IStoreBuilder = object : IStoreBuilder {
            override fun build(storable: Storable) = SharedPreferencesStore(storable.javaClass.name)
        }
    }


    override fun commit(values: Map<String, Any?>) = editContent(values).commit()

    override fun apply(values: Map<String, Any?>) =editContent(values).apply()

    override fun getAll(): Map<String, Any> {
        val values = HashMap<String, Any>()
        sp.all.forEach {
            if(it.value!=null&&it.value is Any){
                values[it.key] = it.value as Any
            }
        }
        return values
    }


    private fun editContent(values: Map<String, Any?>):SharedPreferences.Editor{

        val edit = sp.edit()

        values.forEach {
            when (it.value) {
                is Boolean -> {
                    edit.putBoolean(it.key, it.value as Boolean)
                }
                is Float -> {
                    edit.putFloat(it.key, it.value as Float)
                }
                is String -> {
                    edit.putString(it.key, it.value as String)
                }
                is Int -> {
                    edit.putInt(it.key, it.value as Int)
                }
                is Long -> {
                    edit.putLong(it.key, it.value as Long)
                }
                is Set<*> -> {
                    edit.putStringSet(it.key, it.value as Set<String>)
                }
                is Storable -> {
                    (it.value as Storable).commit(BUILDER)
                }
            }
        }

        return edit
    }
}