package com.zhangzheng.easystore.library

import android.content.Context
import kotlin.reflect.KClass

interface IStoreBuilder {

    fun build(storable: KClass<out Storable>,context: Context):IStore
}