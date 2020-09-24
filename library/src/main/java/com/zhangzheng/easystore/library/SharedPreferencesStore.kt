package com.zhangzheng.easystore.library

import android.content.Context
import android.content.SharedPreferences



class SharedPreferencesStore(var name: String,var context: Context) : IStore {

    class Builder:IStoreBuilder{
        override fun build(storable: Storable, context: Context)
                =SharedPreferencesStore(storable.javaClass.name,context)
    }

   private val sp = context.getSharedPreferences(name,Context.MODE_PRIVATE)


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
            }
        }

        return edit
    }
}