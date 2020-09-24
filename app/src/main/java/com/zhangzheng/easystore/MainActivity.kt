package com.zhangzheng.easystore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zhangzheng.easystore.library.EasyStore
import com.zhangzheng.easystore.library.apply
import com.zhangzheng.easystore.library.fill

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyStore.init(this)

        val bean = TestStorage().fill()

        Log.e("ZZZZZ",bean.name+"<<<<<<")

        bean.name = "9999999"

        bean.apply()
    }

}
