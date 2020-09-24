package com.zhangzheng.easystore.library

interface  Storable{
    fun generateMap(): Map<String, Any?>

    fun fillMap(values:Map<String, Any?>)
}