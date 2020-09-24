package com.zhangzheng.easystore.library

interface IStore{

    fun commit(values:Map<String,Any?>):Boolean

    fun apply(values:Map<String,Any?>)

    fun getAll():Map<String,Any>

}