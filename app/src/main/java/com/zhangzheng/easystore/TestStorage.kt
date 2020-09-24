package com.zhangzheng.easystore

import com.zhangzheng.easystore.library.Storable

interface TestStorage :Storable{
    var name:String
    var count:Int
    var isBool:Boolean
}
