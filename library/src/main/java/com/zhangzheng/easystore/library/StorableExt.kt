package com.zhangzheng.easystore.library

fun Storable.commit(build:IStoreBuilder= SharedPreferencesStore.BUILDER){
    build.build(this).commit(this.generateMap())
}

fun Storable.apply(build:IStoreBuilder = SharedPreferencesStore.BUILDER){
    build.build(this).apply(this.generateMap())
}

private fun Storable.generateMap():Map<String,Any?>{
    val mapValue = mutableMapOf<String,Any?>()
    javaClass.declaredFields.forEach {
        it.isAccessible = true
        mapValue.put(it.name,it.get(this))
    }
    return mapValue
}

fun <T:Storable>T.fill(build:IStoreBuilder = SharedPreferencesStore.BUILDER):T{
    val values = build.build(this).getAll()
    values.forEach{entry->
        val field = javaClass.getDeclaredField(entry.key)
        field.isAccessible = true
        field.set(this,entry.value)
    }
    return this
}