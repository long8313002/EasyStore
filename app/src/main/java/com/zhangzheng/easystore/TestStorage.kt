package com.zhangzheng.easystore

import com.zhangzheng.easystore.library.Storable

interface TestStorage :Storable{
    var name:String
    var count:Float
    var isBool:Boolean
}
