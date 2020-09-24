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

        Log.e("ZZZZZZ_get", loadFromLocal.name + "")


        TestStorage::class.commit {
            name = "22222"
        }


    }

}
