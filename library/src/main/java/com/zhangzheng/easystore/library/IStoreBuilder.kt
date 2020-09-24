package com.zhangzheng.easystore.library

import android.content.Context

interface IStoreBuilder {

    fun build(storable: Storable,context: Context):IStore
}