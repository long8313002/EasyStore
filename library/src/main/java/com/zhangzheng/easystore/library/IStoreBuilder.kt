package com.zhangzheng.easystore.library

interface IStoreBuilder {

    fun build(storable: Storable):IStore
}