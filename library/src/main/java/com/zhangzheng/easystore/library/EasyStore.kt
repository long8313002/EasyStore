package com.zhangzheng.easystore.library

import android.content.Context
import java.lang.RuntimeException

object EasyStore {

   private  lateinit var context: Context

    fun getContext():Context{
        if(context == null){
            throw RuntimeException("EasyStore 未初始化 请调用EasyStore.init")
        }
        return context
    }

    fun init(context: Context) {
        this.context = context.applicationContext
    }
}