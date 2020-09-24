package com.zhangzheng.easystore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zhangzheng.easystore.library.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyStore.init(this)


        val loadFromLocal = TestStorage::class.load()

        Log.e("ZZZZZZ_get", ""+loadFromLocal.count)
        Log.e("ZZZZZZ_get", ""+loadFromLocal.isBool)
        Log.e("ZZZZZZ_get", ""+loadFromLocal.name)

        Log.e("ZZZZZZ_s_get", ""+TestStorage::class.get { name })
        Log.e("ZZZZZZ_s_get", ""+TestStorage::class.get { count })
        Log.e("ZZZZZZ_s_get", ""+TestStorage::class.get { isBool })

        TestStorage::class.apply {
            name = "2777777"
            count =1000F
            isBool =true
        }


    }

}
